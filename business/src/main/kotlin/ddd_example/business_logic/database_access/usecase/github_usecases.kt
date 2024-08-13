package ddd_example.business_logic.database_access.usecase

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import ddd_example.business_logic.database_access.domain.GitHubOrganizationRepository
import ddd_example.business_logic.database_access.domain.GitHubRepository
import ddd_example.business_logic.database_access.domain.GitHubRepositoryNameHistoryRepository
import ddd_example.business_logic.database_access.domain.GitHubRepositoryRepository

sealed interface CreateGitHubRepositoryUseCaseError {
    data object InvalidRequest : CreateGitHubRepositoryUseCaseError
    data object InvalidRepositoryName : CreateGitHubRepositoryUseCaseError
}

/**
 * example1
 * - User will pass (repositoryName, OrganizationName) to the system.
 * - System will check the Organization exists -> if not, return InvalidRequest
 * - System will check the repository name is valid -> if not, return invalidRepositoryName
 * - If all is good, create the repository.
 *
 */
suspend fun createGitHubRepositoryUseCase(
    // dependency
    gitHubRepositoryRepository: GitHubRepositoryRepository,
    gitHubOrganizationRepository: GitHubOrganizationRepository,

    // arguments
    organizationName: String,
    repositoryName: String,
): Either<CreateGitHubRepositoryUseCaseError, Unit> {
    // System will check the Organization exists -> if not, return InvalidRequest
    val organization = gitHubOrganizationRepository.findBy(organizationName)
        ?: return CreateGitHubRepositoryUseCaseError.InvalidRequest.left()

    // System will check the repository name is valid -> if not, return invalidRepositoryName
    gitHubRepositoryRepository.findBy(organization.id, repositoryName)?.let {
        // duplicated name
        return CreateGitHubRepositoryUseCaseError.InvalidRepositoryName.left()
    }
    // other invalid name cases
    val newRepository = GitHubRepository.create(
        name = repositoryName,
        organizationId = organization.id,
    ).getOrElse { return CreateGitHubRepositoryUseCaseError.InvalidRepositoryName.left() }

    // If all is good, create the github repository.
    gitHubRepositoryRepository.create(newRepository)
    return Unit.right()
}

sealed interface FindGitHubRepositoryError {
    data object NotFound : FindGitHubRepositoryError
}

/**
 * example2 findUseCase
 * - User will pass (repositoryName, OrganizationName) to the system.
 * - System will check the Organization exists -> if not, return NotFound
 * - System will check the repository exists -> if not, return NotFound
 * - If all is good, return the repository.
 *
 */
suspend fun findGitHubRepositoryUseCase(
    // dependency
    gitHubRepositoryRepository: GitHubRepositoryRepository,
    gitHubOrganizationRepository: GitHubOrganizationRepository,
    // arguments
    organizationName: String,
    repositoryName: String,
): Either<FindGitHubRepositoryError, GitHubRepository> {
    val organization = gitHubOrganizationRepository.findBy(organizationName)
        ?: return FindGitHubRepositoryError.NotFound.left()
    val repository = gitHubRepositoryRepository.findBy(organization.id, repositoryName)
        ?: return FindGitHubRepositoryError.NotFound.left()
    return repository.right()
}

sealed interface UpdateGitHubRepositoryError {
    data object InvalidRequest : UpdateGitHubRepositoryError
    data object NotFound : UpdateGitHubRepositoryError
    data object InvalidRepositoryName : UpdateGitHubRepositoryError
}

/**
 * example3 update UseCase
 * - User will pass (repositoryName, OrganizationName) and newRepositoryName to the system.
 * - System will check the Organization exists -> if not, return NotFound
 * - System will check the repository exists -> if not, return NotFound
 * - System will check the newRepositoryName is valid -> if not, return InvalidRepositoryName
 * - If all is good, update the repository.
 * - System will update the history.
 *
 */
suspend fun updateGitHubRepositoryUseCase(
    // dependency
    gitHubRepositoryRepository: GitHubRepositoryRepository,
    gitHubOrganizationRepository: GitHubOrganizationRepository,
    gitHubRepositoryNameHistoryRepository: GitHubRepositoryNameHistoryRepository,
    // arguments
    organizationName: String,
    repositoryName: String,
    newRepositoryName: String,
): Either<UpdateGitHubRepositoryError, Unit> {
    val organization = gitHubOrganizationRepository.findBy(organizationName)
        ?: return UpdateGitHubRepositoryError.InvalidRequest.left()
    val repository = gitHubRepositoryRepository.findBy(organization.id, repositoryName)
        ?: return UpdateGitHubRepositoryError.NotFound.left()
    val updated = repository.update(newRepositoryName)
        .getOrElse { return UpdateGitHubRepositoryError.InvalidRepositoryName.left() }
    gitHubRepositoryRepository.update(updated)

    // update history
    gitHubRepositoryNameHistoryRepository.create(
        organizationId = repository.organizationId,
        name = repository.name,
        id = repository.id,
    )
    return Unit.right()
}

sealed interface FindGitHubRepositoryV2Error {
    data object NotFound : FindGitHubRepositoryV2Error
    data class NewUrlFound(val organizationName: String, val repositoryName: String) : FindGitHubRepositoryV2Error
}

// support redirect scenarios too.
suspend fun findGitHubRepositoryV2(
    // dependency
    gitHubRepositoryRepository: GitHubRepositoryRepository,
    gitHubOrganizationRepository: GitHubOrganizationRepository,
    gitHubRepositoryNameHistoryRepository: GitHubRepositoryNameHistoryRepository,
    // arguments
    organizationName: String,
    repositoryName: String,
): Either<FindGitHubRepositoryV2Error, GitHubRepository> {
    val organization = gitHubOrganizationRepository.findBy(organizationName)
        ?: return FindGitHubRepositoryV2Error.NotFound.left()
    gitHubRepositoryRepository.findBy(organization.id, repositoryName)?.let {
        return it.right()
    }
    // if not found, check the history.
    val repositoryId = gitHubRepositoryNameHistoryRepository.findBy(organization.id, repositoryName)
        ?: return FindGitHubRepositoryV2Error.NotFound.left()
    val repository = gitHubRepositoryRepository.findBy(repositoryId)
        ?: return FindGitHubRepositoryV2Error.NotFound.left()
    return FindGitHubRepositoryV2Error.NewUrlFound(organizationName, repository.name).left()
}
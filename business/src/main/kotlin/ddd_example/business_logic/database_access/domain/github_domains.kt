package ddd_example.business_logic.database_access.domain

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right

typealias GitHubRepositoryId = String // no biz spec for the ID format.
typealias GitHubOrganizationId = String // no biz spec for the ID format.

// -- organization
data class GitHubOrganization(
    val id: GitHubOrganizationId,
    val name: String,
)

interface GitHubOrganizationRepository {
    suspend fun findBy(name: String): GitHubOrganization?
}
// we don't need to care the details.
// like
// 1. val organizations: List<GitHubOrganization> = ???, organizations.find { it.name == organizationName }
// 2. ask DB to find the organization by name

// -- repository
sealed class GitHubRepositoryNameValidationError {
    data object EmptyName : GitHubRepositoryNameValidationError()
    data object URLInvalidName : GitHubRepositoryNameValidationError()
    data object DuplicatedName : GitHubRepositoryNameValidationError()
}

data class GitHubRepository(
    val id: GitHubRepositoryId,
    val name: String,
    val organizationId: GitHubOrganizationId,
) {
    companion object {
        fun create(
            name: String,
            organizationId: GitHubOrganizationId,
        ): Either<GitHubRepositoryNameValidationError, GitHubRepository> {
            val validatedName = validateName(name).getOrElse { return it.left() }
            return GitHubRepository(
                id = generateRandomId(),
                name = validatedName,
                organizationId = organizationId,
            ).right()
        }
    }

    fun update(name: String): Either<GitHubRepositoryNameValidationError, GitHubRepository> {
        val validatedName = validateName(name).getOrElse { return it.left() }
        return this.copy(
            name = validatedName
        ).right()
    }
}

fun generateRandomId(): GitHubRepositoryId = TODO()

fun validateName(name: String): Either<GitHubRepositoryNameValidationError, String> = TODO()

interface GitHubRepositoryRepository {
    suspend fun create(repository: GitHubRepository)
    suspend fun update(repository: GitHubRepository)
    suspend fun findBy(organizationId: GitHubOrganizationId, name: String): GitHubRepository?
    suspend fun findBy(id: GitHubRepositoryId): GitHubRepository?
}

// -- repository history

interface GitHubRepositoryNameHistoryRepository {
    suspend fun create(
        organizationId: GitHubOrganizationId,
        name: String,
        id: GitHubRepositoryId,
    )
    suspend fun findBy(organizationId: GitHubOrganizationId, name: String): GitHubRepositoryId?
}


package ddd_example.business_logic.example.write.usecase

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import ddd_example.business_logic.example.write.entity.AuthorId
import ddd_example.business_logic.example.write.repository.AuthorRepository

sealed interface AddAuthorError {
    data object InvalidRequest : AddAuthorError
}

object AuthorUseCase {
    context(AuthorRepository)
    suspend fun create(
        firstName: String,
        lastName: String,
    ): Either<AddAuthorError, AuthorId> {
        val author =
            ddd_example.business_logic.example.write.entity.Author.newAuthor(
                this@AuthorRepository.generateId(),
                firstName,
                lastName,
            ).getOrElse { return AddAuthorError.InvalidRequest.left() }
        this@AuthorRepository.create(author)
        return author.id.right()
    }
}

package ddd_example.domain.example.write.entity

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@JvmInline
value class AuthorId(val value: Int)

sealed interface AuthorValidationError {
    data object FirstNameIsEmpty : AuthorValidationError
    data object LastNameIsEmpty : AuthorValidationError
}

data class Author(
    val id: AuthorId,
    val firstName: String,
    val lastName: String,
    val createdAt: OffsetDateTime,
) {
    companion object {
        fun newAuthor(
            id: AuthorId,
            firstName: String,
            lastName: String,
        ): Either<AuthorValidationError, Author> {
            if (firstName.isBlank()) return AuthorValidationError.FirstNameIsEmpty.left()
            if (lastName.isBlank()) return AuthorValidationError.LastNameIsEmpty.left()
            return Author(
                id,
                firstName,
                lastName,
                OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS),
            ).right()
        }
    }
}

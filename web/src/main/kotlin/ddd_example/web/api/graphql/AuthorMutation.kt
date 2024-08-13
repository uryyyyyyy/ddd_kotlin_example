package ddd_example.web.api.graphql

import arrow.core.getOrElse
import ddd_example.business_logic.example.write.repository.AuthorRepository
import ddd_example.business_logic.example.write.usecase.AuthorUseCase
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class AuthorMutation(
    private val authorRepository: AuthorRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @MutationMapping
    suspend fun addAuthor(
        @Argument firstName: String,
        @Argument lastName: String,
    ): Int {
        logger.info("add author: $firstName $lastName")
        return with(authorRepository) {
            AuthorUseCase.create(firstName, lastName)
                .getOrElse { throw GraphqlCustomError("Invalid request") }
                .value
        }
    }
}

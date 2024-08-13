package ddd_example.web.api.graphql

import arrow.core.getOrElse
import ddd_example.business_logic.example.read.repository.BookRepository
import ddd_example.business_logic.example.read.usecase.BookUseCase
import graphql.schema.DataFetchingEnvironment
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

data class Book(
    val id: Int,
    val name: String,
    val pageCount: Int,
    val authorId: Int,
)

data class BookFilter(
    val name: String?,
    val pageCount: Int?,
)

@Controller
class BookQuery(
    private val bookRepository: BookRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @QueryMapping
    suspend fun bookById(
        dfe: DataFetchingEnvironment,
        @Argument id: Int,
    ): Book {
        logger.info("bookById id: $id")
        val auth = dfe.graphQlContext.get<Auth>(AUTH_KEY)
        logger.info(auth.user.toString())
        return with(bookRepository) {
            BookUseCase.getById(id, auth.user!!)
                .getOrElse { throw GraphqlCustomError(it.toString()) }
                .let { Book(it.id.value, it.name, it.pageCount, it.authorId.value) }
        }
    }

    @QueryMapping
    suspend fun books(
        @Argument filter: BookFilter,
    ): List<Book> {
        return with(bookRepository) {
            BookUseCase.getAll(filter.name, filter.pageCount)
                .map { Book(it.id.value, it.name, it.pageCount, it.authorId.value) }
        }
    }
}

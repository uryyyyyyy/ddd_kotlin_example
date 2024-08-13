package ddd_example.web.api.graphql

import ddd_example.business_logic.example.read.repository.ReadAuthorRepository
import ddd_example.business_logic.example.read.usecase.ReadAuthorUseCase
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.stereotype.Controller

data class Author(
    val id: Int,
    val firstName: String,
    val lastName: String,
)

@Controller
class AuthorQuery(
    private val readAuthorRepository: ReadAuthorRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @BatchMapping
    suspend fun author(books: List<Book>): Map<Book, Author> {
        logger.info("authors: ${books.map { it.id }}")
        return with(readAuthorRepository) {
            val authors = ReadAuthorUseCase.getByIds(books.map { it.authorId }.toSet())
            books.associateWith { book ->
                authors.find { it.id.value == book.authorId }
                    ?.let { Author(it.id.value, it.firstName, it.lastName) }
                    ?: error("Author not found for book ${book.id}")
            }
        }
    }
}

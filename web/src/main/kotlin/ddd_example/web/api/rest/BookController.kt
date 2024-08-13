package ddd_example.web.api.rest

import arrow.core.getOrElse
import ddd_example.business_logic.example.read.entity.ReadUser
import ddd_example.business_logic.example.read.repository.BookRepository
import ddd_example.business_logic.example.read.usecase.BookUseCase
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Book(
    val id: Int,
    val name: String,
    val pageCount: Int,
)

@Serializable
data class Book2(
    val id: Int,
    val name: String,
    val pageCount: Int,
)

@RestController
@RequestMapping(path = ["book"])
class BookController(
    private val bookRepository: BookRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun index(request: ServerHttpRequest): Book {
        logger.info("book controller called")
        val book2 = Book2(1, "Book 1", 100)
        val str = Json.encodeToString(book2)
        logger.info(str)
        return with(bookRepository) {
            BookUseCase.getById(1, ReadUser(1, "John Doe"))
                .getOrElse { throw RuntimeException(it.toString()) }
                .let { Book(it.id.value, it.name, it.pageCount) }
        }
    }
}

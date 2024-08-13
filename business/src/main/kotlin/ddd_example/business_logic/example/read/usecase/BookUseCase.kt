package ddd_example.business_logic.example.read.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import ddd_example.business_logic.example.read.entity.BookId
import ddd_example.business_logic.example.read.entity.ReadBook
import ddd_example.business_logic.example.read.entity.ReadUser
import ddd_example.business_logic.example.read.repository.BookRepository
import org.slf4j.LoggerFactory

sealed interface GetBookByIdError {
    data object NotFound : GetBookByIdError
}

object BookUseCase {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    context(BookRepository)
    suspend fun getById(
        id: Int,
        user: ReadUser,
    ): Either<GetBookByIdError, ReadBook> {
        logger.info("getById id: $id")
        return this@BookRepository.getById(BookId(id))?.right() ?: GetBookByIdError.NotFound.left()
    }

    context(BookRepository)
    suspend fun getAll(
        name: String?,
        pageCount: Int?,
    ): List<ReadBook> {
        logger.info("getAll name: $name, pageCount: $pageCount")
        return this@BookRepository.getAll(name, pageCount)
    }
}

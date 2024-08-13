package ddd_example.web.infrastructure.example

import ddd_example.business_logic.example.read.entity.BookId
import ddd_example.business_logic.example.read.entity.ReadBook
import ddd_example.business_logic.example.read.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookRepositoryImpl : BookRepository {
    override suspend fun getById(id: BookId): ReadBook? {
        TODO()
    }

    override suspend fun getAll(
        name: String?,
        pageCount: Int?,
    ): List<ReadBook> {
        TODO()
    }
}

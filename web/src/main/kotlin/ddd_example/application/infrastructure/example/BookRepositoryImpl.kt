package ddd_example.application.infrastructure.example

import ddd_example.domain.example.read.entity.BookId
import ddd_example.domain.example.read.entity.ReadBook
import ddd_example.domain.example.read.repository.BookRepository
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

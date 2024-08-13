package ddd_example.domain.example.read.repository

import ddd_example.domain.example.read.entity.BookId
import ddd_example.domain.example.read.entity.ReadBook

interface BookRepository {
    suspend fun getById(id: BookId): ReadBook?
    suspend fun getAll(
        name: String?,
        pageCount: Int?,
    ): List<ReadBook>
}

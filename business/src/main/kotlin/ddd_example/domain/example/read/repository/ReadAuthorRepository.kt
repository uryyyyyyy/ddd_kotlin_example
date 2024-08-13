package ddd_example.domain.example.read.repository

import ddd_example.domain.example.read.entity.ReadAuthor

interface ReadAuthorRepository {
    suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor>
}

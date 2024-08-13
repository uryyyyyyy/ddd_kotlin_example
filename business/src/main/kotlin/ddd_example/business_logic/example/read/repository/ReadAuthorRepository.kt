package ddd_example.business_logic.example.read.repository

import ddd_example.business_logic.example.read.entity.ReadAuthor

interface ReadAuthorRepository {
    suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor>
}

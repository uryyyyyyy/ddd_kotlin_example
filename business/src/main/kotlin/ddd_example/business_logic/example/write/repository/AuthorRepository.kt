package ddd_example.business_logic.example.write.repository

import ddd_example.business_logic.example.write.entity.Author
import ddd_example.business_logic.example.write.entity.AuthorId

interface AuthorRepository {
    // DBの最新の連番取る
    suspend fun generateId(): AuthorId
    suspend fun create(author: Author)
    suspend fun getById(id: AuthorId): Author?
}

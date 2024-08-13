package ddd_example.application.infrastructure.example

import ddd_example.domain.example.write.entity.Author
import ddd_example.domain.example.write.entity.AuthorId
import ddd_example.domain.example.write.repository.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthorRepositoryImpl : AuthorRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override suspend fun generateId(): AuthorId {
        TODO()
    }

    override suspend fun create(author: Author) {
        TODO()
    }

    override suspend fun getById(id: AuthorId): Author? {
        TODO()
    }
}

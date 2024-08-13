package ddd_example.application.infrastructure.console

import ddd_example.domain.example.read.entity.ReadUser
import ddd_example.domain.example.read.repository.ReadUserRepository
import org.springframework.stereotype.Service

@Service
class ReadUserRepositoryImpl : ReadUserRepository {
    override suspend fun findByToken(token: String): ReadUser? {
        TODO()
    }
}

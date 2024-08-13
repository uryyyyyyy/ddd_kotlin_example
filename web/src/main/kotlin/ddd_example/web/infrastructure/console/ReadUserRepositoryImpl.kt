package ddd_example.web.infrastructure.console

import ddd_example.business_logic.example.read.entity.ReadUser
import ddd_example.business_logic.example.read.repository.ReadUserRepository
import org.springframework.stereotype.Service

@Service
class ReadUserRepositoryImpl : ReadUserRepository {
    override suspend fun findByToken(token: String): ReadUser? {
        TODO()
    }
}

package ddd_example.web.infrastructure.example

import ddd_example.business_logic.example.read.entity.ReadAuthor
import ddd_example.business_logic.example.read.repository.ReadAuthorRepository
import org.springframework.stereotype.Service

@Service
class ReadAuthorRepositoryImpl : ReadAuthorRepository {
    override suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor> {
        TODO()
    }
}

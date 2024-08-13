package ddd_example.application.infrastructure.example

import ddd_example.domain.example.read.entity.ReadAuthor
import ddd_example.domain.example.read.repository.ReadAuthorRepository
import org.springframework.stereotype.Service

@Service
class ReadAuthorRepositoryImpl : ReadAuthorRepository {
    override suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor> {
        TODO()
    }
}

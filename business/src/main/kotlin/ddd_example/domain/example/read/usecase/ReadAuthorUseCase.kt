package ddd_example.domain.example.read.usecase

import ddd_example.domain.example.read.entity.ReadAuthor
import ddd_example.domain.example.read.repository.ReadAuthorRepository

object ReadAuthorUseCase {
    context(ReadAuthorRepository)
    suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor> {
        return this@ReadAuthorRepository.getByIds(ids)
    }
}

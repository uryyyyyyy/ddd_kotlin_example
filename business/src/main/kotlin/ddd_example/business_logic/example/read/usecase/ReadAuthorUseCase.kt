package ddd_example.business_logic.example.read.usecase

import ddd_example.business_logic.example.read.entity.ReadAuthor
import ddd_example.business_logic.example.read.repository.ReadAuthorRepository

object ReadAuthorUseCase {
    context(ReadAuthorRepository)
    suspend fun getByIds(ids: Set<Int>): Set<ReadAuthor> {
        return this@ReadAuthorRepository.getByIds(ids)
    }
}

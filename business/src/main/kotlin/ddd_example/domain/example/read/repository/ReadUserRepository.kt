package ddd_example.domain.example.read.repository

import ddd_example.domain.example.read.entity.ReadUser


interface ReadUserRepository {
    suspend fun findByToken(token: String): ReadUser?
}

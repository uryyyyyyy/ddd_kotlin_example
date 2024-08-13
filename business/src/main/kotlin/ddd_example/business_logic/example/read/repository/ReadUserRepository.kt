package ddd_example.business_logic.example.read.repository

import ddd_example.business_logic.example.read.entity.ReadUser


interface ReadUserRepository {
    suspend fun findByToken(token: String): ReadUser?
}

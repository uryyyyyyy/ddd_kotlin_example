package ddd_example.domain.example.read.entity

import ddd_example.domain.example.write.entity.AuthorId

data class ReadAuthor(
    val id: AuthorId,
    val firstName: String,
    val lastName: String,
)

package ddd_example.business_logic.example.read.entity

import ddd_example.business_logic.example.write.entity.AuthorId

data class ReadAuthor(
    val id: AuthorId,
    val firstName: String,
    val lastName: String,
)

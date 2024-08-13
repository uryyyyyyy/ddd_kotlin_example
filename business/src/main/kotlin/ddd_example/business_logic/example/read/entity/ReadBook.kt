package ddd_example.business_logic.example.read.entity

import ddd_example.business_logic.example.write.entity.AuthorId

@JvmInline
value class BookId(val value: Int)

data class ReadBook(
    val id: BookId,
    val name: String,
    val pageCount: Int,
    val authorId: AuthorId,
)

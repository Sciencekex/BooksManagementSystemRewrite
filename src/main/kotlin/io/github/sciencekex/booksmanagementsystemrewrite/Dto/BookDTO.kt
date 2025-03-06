package io.github.sciencekex.booksmanagementsystemrewrite.Dto

import io.github.sciencekex.booksmanagementsystemrewrite.entity.Book

data class BookDTO(
    val bookNumber: String,
    val bookName: String,
    val bookType: String,
    val bookPrize: Double,
    val author: String? = null,
    val bookPublisher: String? = null
) {
    fun toEntity(): Book = Book(
        bookNumber = bookNumber,
        bookName = bookName,
        bookType = bookType,
        bookPrize = bookPrize,
        author = author,
        bookPublisher = bookPublisher
    )
}

package io.github.sciencekex.booksmanagementsystemrewrite.service

import io.github.sciencekex.booksmanagementsystemrewrite.entity.Book
import io.github.sciencekex.booksmanagementsystemrewrite.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun createBook(bookDTO: BookDTO) {
        bookRepository.save(bookDTO.toEntity())
    }
}

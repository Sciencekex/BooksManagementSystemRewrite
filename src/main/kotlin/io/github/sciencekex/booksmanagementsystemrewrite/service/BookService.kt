package io.github.sciencekex.booksmanagementsystemrewrite.service

import io.github.sciencekex.booksmanagementsystemrewrite.Dto.BookDTO
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

    fun updateBook(id: Long, bookDTO: BookDTO) {
        val existingBook = bookRepository.findById(id).orElseThrow {
            BookNotFoundException("未找到ID为$id 的图书")
        }
        bookRepository.save(bookDTO.toEntity().copy(id = id))
    }

    fun deleteBook(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw BookNotFoundException("未找到ID为$id 的图书")
        }
        bookRepository.deleteById(id)
    }
}
class BookNotFoundException(message: String) : RuntimeException(message)
package io.github.sciencekex.booksmanagementsystemrewrite.repository

import io.github.sciencekex.booksmanagementsystemrewrite.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByBookNumber(bookNumber: String): Book?
}
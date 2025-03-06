package io.github.sciencekex.booksmanagementsystemrewrite

import io.github.sciencekex.booksmanagementsystemrewrite.entity.Book
import io.github.sciencekex.booksmanagementsystemrewrite.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BooksManagementSystemRewriteApplication : CommandLineRunner {
    @Autowired
    lateinit var bookRepository: BookRepository

    override fun run(vararg args: String?) {
        bookRepository.saveAll(listOf(
            Book(
                bookNumber = "001",
                bookName = "活着",
                bookType = "小说",
                bookPrize = 39.9,
                author = "余华",
                bookPublisher = "某某出版社"
            ),
            Book(
                bookNumber = "002",
                bookName = "三体",
                bookType = "科幻",
                bookPrize = 99.8,
                author = "刘慈欣",
                bookPublisher = "重庆出版社"
            )
        ))
    }
}

fun main(args: Array<String>) {
    runApplication<BooksManagementSystemRewriteApplication>(*args)
}

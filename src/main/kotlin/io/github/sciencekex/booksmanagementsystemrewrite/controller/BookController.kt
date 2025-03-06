package io.github.sciencekex.booksmanagementsystemrewrite.controller

import io.github.sciencekex.booksmanagementsystemrewrite.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BookController(
    private val bookService: BookService
) {
    @GetMapping
    fun getAllBooks(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "status" to "success",
            "message" to "数据查询成功",
            "results" to bookService.getAllBooks()
        ))
    }

    @PostMapping
    fun createBook(
        @RequestBody bookDTO: BookDTO
    ): ResponseEntity<Map<String, String>> {
        bookService.createBook(bookDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(maoOf(
            "status" to "success",
            "message" to "数据添加成功"
        ))
    }
}
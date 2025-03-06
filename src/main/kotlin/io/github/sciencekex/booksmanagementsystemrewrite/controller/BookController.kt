package io.github.sciencekex.booksmanagementsystemrewrite.controller

import io.github.sciencekex.booksmanagementsystemrewrite.Dto.BookDTO
import io.github.sciencekex.booksmanagementsystemrewrite.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid

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
        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf(
            "status" to "success",
            "message" to "数据添加成功"
        ))
    }
    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: Long,
        @Valid @RequestBody bookDTO: BookDTO
    ): ResponseEntity<Map<String, String>> {
        bookService.updateBook(id, bookDTO)
        return ResponseEntity.ok(mapOf(
            "status" to "success",
            "message" to "数据更新成功"
        ))
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        bookService.deleteBook(id)
        return ResponseEntity.ok(mapOf(
            "status" to "success",
            "message" to "数据删除成功"
        ))
    }
}
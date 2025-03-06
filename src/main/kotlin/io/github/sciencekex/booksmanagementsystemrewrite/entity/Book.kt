package io.github.sciencekex.booksmanagementsystemrewrite.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity
data class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val bookNUmber: String,
    val bookName: String,
    val bookType: String,
    val bookPrize: Double,
    val author: String,
    val bookPublisher: String
)
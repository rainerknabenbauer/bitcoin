package de.nykon.bitcoin.backend.users.value

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "users")
data class User(
        val id: UUID,
        val name: String,
        val password: String,
        val createdDate: LocalDateTime
)
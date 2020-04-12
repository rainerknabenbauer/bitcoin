package de.nykon.bitcoin.backend.repository.value

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "users")
data class User(
        val id: UUID,
        val name: String,
        val password: String,
        val createdDate: LocalDateTime,
        val session: Session?
)
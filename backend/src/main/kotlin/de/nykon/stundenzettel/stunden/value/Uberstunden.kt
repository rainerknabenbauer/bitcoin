package de.nykon.stundenzettel.stunden.value

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.UUID

@Document(collection = "uberstunden")
data class Uberstunden(
        val id: UUID = UUID.randomUUID(),
        val date: LocalDate,
        val hours: Double,
        val reason: String
)
package de.nykon.bitcoin.backend.repository.value

import java.time.LocalDateTime

data class Session(
        val timestamp: LocalDateTime,
        val token: String
)
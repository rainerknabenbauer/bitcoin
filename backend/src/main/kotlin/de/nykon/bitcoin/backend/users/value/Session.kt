package de.nykon.bitcoin.backend.users.value

import java.time.LocalDateTime

data class Session(
        val timestamp: LocalDateTime,
        val token: String
)
package de.nykon.bitcoin.backend.users.value

data class Authentication(
        val name: String,
        val secret: String,
        val token: String
)
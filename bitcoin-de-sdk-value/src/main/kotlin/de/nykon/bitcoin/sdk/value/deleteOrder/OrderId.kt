package de.nykon.bitcoin.sdk.value.deleteOrder

/**
 * Wraps any arbitrary order ID.
 * Bitcoin.de uses a 7-digit format, i. E. 'A1234BC'.
 */
data class OrderId(
        val value: String
)

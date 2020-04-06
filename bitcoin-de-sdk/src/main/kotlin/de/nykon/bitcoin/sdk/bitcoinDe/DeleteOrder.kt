package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.DeleteOrder

data class DeleteOrder(
        override val apiKey: String,
        override val apiSecret: String,
        override val orderId: String)
    : DeleteOrder {

    override fun execute() {

    }
}
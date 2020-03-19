package de.nykon.bitcoin.client

class ClientApplication

fun main() {

    val bitcoinDeClient = BitcoinDeClient()
    val offers = bitcoinDeClient.getOffers()

    if (!offers.isNullOrEmpty()) {
        val backendClient = BackendClient()
        backendClient.persist(offers)
    }
}
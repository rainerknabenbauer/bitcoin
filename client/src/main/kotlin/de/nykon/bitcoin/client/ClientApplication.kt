package de.nykon.bitcoin.client

class ClientApplication

private val bitcoinDeClient = BitcoinDeClient()

fun main(args: Array<String>) {

    if (args.size < 2) {
        println("Set on start: [1] ApiKey\n[2] ApiSecret")
    } else {
        val apiKey = args[0]
        val apiSecret = args[1]

        saveCurrentState(apiKey, apiSecret)
    }
}

/**
 * Get current prices and persist locally.
 */
fun saveCurrentState(apiKey: String, apiSecret: String) {

    val offers = bitcoinDeClient.getOffers(apiKey, apiSecret)

    if (!offers.isNullOrEmpty()) {
        val backendClient = BackendClient()
        backendClient.persist(offers)
    }
}

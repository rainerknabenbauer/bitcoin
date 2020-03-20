package de.nykon.bitcoin.client

class ClientApplication

fun main(args : Array<String>) {

    var cycleInMilliseconds: Long = 300000

    if (args.size < 2) {
        println("Set on start: [1] ApiKey\n[2] ApiSecret")
    } else {

        while (true) {
            val bitcoinDeClient = BitcoinDeClient()
            val offers = bitcoinDeClient.getOffers(args[0], args[1])

            if (!offers.isNullOrEmpty()) {
                val backendClient = BackendClient()
                backendClient.persist(offers, cycleInMilliseconds)
            }

            Thread.sleep(cycleInMilliseconds)
        }


    }
}
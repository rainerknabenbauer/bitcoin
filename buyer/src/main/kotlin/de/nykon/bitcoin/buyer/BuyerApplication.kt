package de.nykon.bitcoin.buyer

class BuyerApplication

fun main(args: Array<String>) {

    if (args.size < 2) {
        println("Set on start: [1] ApiKey\n[2] ApiSecret")
    } else {
        val apiKey = args[0]
        val apiSecret = args[1]

        //TODO Run in a loop
        //TODO Get available amount
        //TODO Get buy trades
    }

}
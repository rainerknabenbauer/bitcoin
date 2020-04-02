package de.nykon.bitcoin.seller

class SellerApplication

fun main(args: Array<String>) {

    if (args.size < 3) {
        println("Set on start: [1] ApiKey\n[2] ApiSecret\n[3] ExitPrice (float)")
    } else {
        val apiKey = args[0]
        val apiSecret = args[1]
        val exitPrice = args[2]

        //TODO Exit once minPrice is reached
        //TODO Adjust minPrice on raising prices

    }

}
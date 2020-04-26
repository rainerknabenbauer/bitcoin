package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.RestClient
import de.nykon.bitcoin.sdk.value.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Transaction<T>: Authentication {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    val uri = "https://nykon.de/bitcoin/${this::class.simpleName}.php"
    val jsonFile = "/json/${this::class.simpleName}.json"

    abstract fun convert(body: String?): T

    fun execute(json: String): Response<T> {

        var restClient = RestClient()

        val receive = restClient.post(uri, json)

        /*
          Nonce is timestamped and part of the cryptographic signature.
          A nonce has the be unique and must be followed by increased in the next request.
          This is a fugly workaround until this gets independent of a timestamp in seconds.
         */
        try {
            Thread.sleep(1_100)
        } catch (e: InterruptedException) {
            log.error("Sleep was interrupted")
        }


        return Response(receive.statusCode.value(), convert(receive.body))
    }

}
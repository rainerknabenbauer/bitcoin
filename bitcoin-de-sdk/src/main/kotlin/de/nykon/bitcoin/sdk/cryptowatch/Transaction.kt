package de.nykon.bitcoin.sdk.cryptowatch

import de.nykon.bitcoin.sdk.RestClient
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Transaction<T> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    abstract fun convert(body: String?): T

    fun execute(uri: String): Response<T> {

        val restClient = RestClient()

        val receive = restClient[uri]

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
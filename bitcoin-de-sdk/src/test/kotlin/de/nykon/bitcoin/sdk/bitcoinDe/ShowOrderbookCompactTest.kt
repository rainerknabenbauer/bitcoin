package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ShowOrderbookCompactTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowOrderbookCompact(apiKey, apiSecret)

    @Test
    fun `retrieve compact list of BUY and SELL offers from exchange`() {
        // arrange

        // act
        val response = testee.all()

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.orders.asks.isNotEmpty(), "receive BUY offers from exchange")
            assertTrue(response.body.orders.bids.isNotEmpty(), "receive SELL offers from exchange")
        }
    }

}
package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ShowOrderbookTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowOrderbook(apiKey, apiSecret)

    @Test
    fun `get all public BUY offers from exchange`() {
        // arrange

        // act
        val response = testee.buy()

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.orders.isNotEmpty(), "Receive BUY offers from exchange")
        }
    }

    @Test
    fun `get all public SELL offers from exchange`() {
        // arrange

        // act
        val response = testee.sell()

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.orders.isNotEmpty(), "Receive BUY offers from exchange")
        }
    }

}
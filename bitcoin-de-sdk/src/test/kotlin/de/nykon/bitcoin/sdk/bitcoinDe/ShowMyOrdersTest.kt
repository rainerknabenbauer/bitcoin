package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ShowMyOrdersTest {

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
            Assertions.assertEquals(200, response.statusCode, "receives HTTP 200 response")
            Assertions.assertTrue(response.body.orders.asks.isNotEmpty(), "List all BUY offers associated with this account")
            Assertions.assertTrue(response.body.orders.bids.isNotEmpty(), "List all SELL offers associated with this account")
        }
    }

}
package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ShowAccountInfoTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowAccountInfo(apiKey, apiSecret)

    @Test
    fun `successfully retrieve account info`() {
        // arrange

        // act
        val response = testee.execute()

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.data.balances.btc.total_amount.isNotEmpty(), "BTC ledger exists ")
        }
    }

}
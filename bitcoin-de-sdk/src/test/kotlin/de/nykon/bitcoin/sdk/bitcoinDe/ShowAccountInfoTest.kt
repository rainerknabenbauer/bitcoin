package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ShowAccountInfoTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowAccountInfo(apiKey, apiSecret)

    @Test
    fun `retrieve account info`() {
        // arrange

        // act
        val response = testee.execute()

        println(response.body.data.balances.btc.total_amount)

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertNotNull(response.body.data.balances.btc.total_amount, "BTC ledger exists ")
        }
    }

}
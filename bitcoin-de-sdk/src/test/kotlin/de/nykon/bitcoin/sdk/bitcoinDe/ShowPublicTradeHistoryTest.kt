package de.nykon.bitcoin.sdk.bitcoinDe

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ShowPublicTradeHistoryTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowPublicTradeHistory(apiKey, apiSecret)

    @Test
    fun `get all public trades`() {
        // arrange

        // act
        val publicTradeHistory = testee.all()

        // assert
        SoftAssertions().apply {
            assertTrue(publicTradeHistory.body.errors.isNullOrEmpty())
            assertTrue(publicTradeHistory.statusCode == 200)
            assertTrue(publicTradeHistory.body.trades.isNotEmpty())
        }
    }

}
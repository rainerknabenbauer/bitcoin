package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.value.bitcoinde.showMyTrades.TradeState
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

internal class ShowMyTradesTest {

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = ShowMyTrades(apiKey, apiSecret)

    @Test
    fun `get successful trades`() {
        // arrange
        val yesterday = LocalDateTime.now()
                .minusDays(1)
                .truncatedTo(ChronoUnit.SECONDS);

        val state = TradeState.SUCCESSFUL

        // act
        val actual = testee.execute(yesterday, state)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(200, actual.statusCode)
        }
    }

    @Test
    fun `get cancelled trades`() {
        // arrange
        val yesterday = LocalDateTime.now()
                .minusDays(1)
                .truncatedTo(ChronoUnit.SECONDS);

        val state = TradeState.CANCELLED

        // act
        val actual = testee.execute(yesterday, state)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(200, actual.statusCode)
        }
    }

    @Test
    fun `get pending trades`() {
        // arrange
        val yesterday = LocalDateTime.now()
                .minusDays(1)
                .truncatedTo(ChronoUnit.SECONDS);

        val state = TradeState.PENDING

        // act
        val actual = testee.execute(yesterday, state)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(200, actual.statusCode)
        }
    }

}
package de.nykon.bitcoin.sdk.cryptowatch

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SummaryTest {

    private var testee: ShowKrakenSummary = ShowKrakenSummary()

    @Test
    fun `receive summary`() {
        // arrange

        // act
        val response = testee.execute()

        println(response.body)

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
        }
    }

}
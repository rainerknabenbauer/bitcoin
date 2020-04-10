package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ExecuteTradeTest {

    val apiKey = System.getenv("bitcoin.api.key")
    val apiSecret = System.getenv("bitcoin.api.secret")

    val testee = ExecuteTrade(apiKey, apiSecret)

    @Test
    fun `execute BUY`() {
        // arrange
        val orderID = OrderId("fake-order-id")
        val amount = BigDecimal.valueOf(0.1)

        // act
        val response = testee.buy(orderID, amount)

        val hasExpectedErrorMessage = response.body.errors!!
                .flatMap { listOf(it.message) }
                .contains("Invalid order_id")

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertEquals(1, response.body.errors?.size, "contains exactly one error")
            assertTrue(hasExpectedErrorMessage, "has expected error message")
        }
    }

    @Test
    fun `execute SELL`() {
        // arrange
        val orderID = OrderId("fake-order-id")
        val amount = BigDecimal.valueOf(0.1)

        // act
        val response = testee.sell(orderID, amount)

        val hasExpectedErrorMessage = response.body.errors!!
                .flatMap { listOf(it.message) }
                .contains("Invalid order_id")

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertEquals(1, response.body.errors?.size, "contains exactly one error")
            assertFalse(hasExpectedErrorMessage, "has expected error message")
        }
    }

}
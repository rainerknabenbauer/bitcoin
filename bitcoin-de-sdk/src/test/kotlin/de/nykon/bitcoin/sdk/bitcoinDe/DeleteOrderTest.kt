package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DeleteOrderTest {

    val apiKey = System.getenv("bitcoin.api.key")
    val apiSecret = System.getenv("bitcoin.api.secret")

    val testee = DeleteOrder(apiKey, apiSecret)

    @Test
    fun `delete a non existing order`() {
        // arrange
        val orderId = OrderId("89TRQ9")

        // act
        val response = testee.execute(orderId)

        val hasExpectedErrorMessage = response.body.errors!!
                .flatMap { listOf(it.message) }
                .contains("Invalid order_id")

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertEquals(1, response.body.errors?.size, "contains exactly one error")
            Assertions.assertTrue(hasExpectedErrorMessage, "has expected error message")
        }
    }

}
package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DeleteOrderTest {

    val apiKey = System.getenv("bitcoin.api.key")
    val apiSecret = System.getenv("bitcoin.api.secret")

    val testee = DeleteOrder(apiKey, apiSecret)

    @Test
    fun `delete a non existing order`() {
        // arrange
        val orderId = OrderId("fake-order-id")

        // act
        val response = testee.execute(orderId)

        // assert
        assertEquals(200, response.statusCode)
        assertEquals(1, response.body.errors?.size)
    }

}
package de.nykon.bitcoin.sdk.bitcoinDe

import de.nykon.bitcoin.sdk.value.deleteOrder.OrderId
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

internal class CreateOrderTest {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    private val testee = CreateOrder(apiKey, apiSecret)
    private val deleteOrder = DeleteOrder(apiKey, apiSecret)

    @Test
    fun `minimum trade volume not reached gets caught early`() {
        // arrange
        val price = BigDecimal.valueOf(50)
        val amount = BigDecimal.valueOf(1)

        // act
        val response = testee.buy(price, amount)

        // assert
        SoftAssertions().apply {
            assertEquals(400, response.statusCode, "returns Bad Request")
            assertTrue(response.body.order_id.isEmpty(), "has not been sent to api")
            assertEquals(response.body.errors.size, 1, "returns minimum not reached")
        }
    }

    @Test
    fun `create a SELL order and delete it again`() {
        // arrange
        val price = BigDecimal.valueOf(10000)
        val amount = BigDecimal.valueOf(0.01)

        // act
        val response = testee.sell(price, amount)
        log.info("Created SELL order ${response.body.order_id}")

        val deleteResponse = deleteOrder.execute(OrderId(response.body.order_id))
        log.info("Deleted SELL order ${response.body.order_id} " +
                "with ${deleteResponse.body.errors.size} errors")


        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.order_id.isNotEmpty(), "SELL offer has been created")
            assertEquals(deleteResponse.body.errors.size, 0, "delete offer was successful")
        }
    }

    @Test
    fun `create a BUY order and delete it again`() {
        // arrange
        val price = BigDecimal.valueOf(4000)
        val amount = BigDecimal.valueOf(0.1)

        // act
        val response = testee.buy(price, amount)
        log.info("Created BUY order ${response.body.order_id}")
        val deleteResponse = deleteOrder.execute(OrderId(response.body.order_id))
        log.info("Deleted BUY order ${response.body.order_id} " +
                "with ${deleteResponse.body.errors.size} errors")

        // assert
        SoftAssertions().apply {
            assertEquals(200, response.statusCode, "receives HTTP 200 response")
            assertTrue(response.body.order_id.isNotEmpty(), "BUY offer has been created")
            assertEquals(deleteResponse.body.errors.size, 0, "delete offer was successful")
        }
    }

}
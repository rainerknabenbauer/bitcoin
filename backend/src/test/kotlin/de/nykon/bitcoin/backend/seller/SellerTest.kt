package de.nykon.bitcoin.backend.seller

import com.nhaarman.mockito_kotlin.mock
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.Response
import de.nykon.bitcoin.sdk.value.bitcoinde.TransactionType
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.Order
import de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook.ShowOrderbookBody
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class SellerTest {

    @Autowired
    private lateinit var config: SellerSchedulConfig

    private var showAccountInfo: ShowAccountInfo = mock()
    private var showMyOrders: ShowMyOrders = mock()
    private var showOrderbook: ShowOrderbook = mock()
    private var deleteOrder: DeleteOrder = mock()
    private var createOrder: CreateOrder = mock()

    @Test
    fun `deactive sell schedule once all coins are gone`() {
        // arrange
        val testee = Seller(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        // act
        testee.createOrder(BigDecimal.ZERO, BigDecimal.valueOf(1000), 0)

        // assert
        SoftAssertions().apply {
            assertFalse(config.isActive)
        }
    }

    @Test
    fun `calculate average price`() {
        val testee = Seller(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        // arrange
        val orders = Order.generate(5, 1100.0, 1500.0, TransactionType.SELL.name)
        val showOrderbookBody = ShowOrderbookBody(0, emptyList(), orders)
        val response = Response(200, showOrderbookBody)

        // act
        val findAveragePrice = testee.findAveragePrice(response)

        var sum = BigDecimal.ZERO
        orders.forEach{ order -> sum = sum.add(order.price) }

        val expectedAverage = sum.div(BigDecimal(orders.size))

        // assert
        SoftAssertions().apply {
            assertEquals(expectedAverage, findAveragePrice)
        }
    }
}
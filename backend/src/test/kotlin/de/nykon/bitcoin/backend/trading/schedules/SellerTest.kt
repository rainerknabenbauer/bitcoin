package de.nykon.bitcoin.backend.trading.schedules

import com.nhaarman.mockito_kotlin.mock
import de.nykon.bitcoin.backend.trading.schedules.config.SellerSchedulConfig
import de.nykon.bitcoin.sdk.bitcoinDe.*
import de.nykon.bitcoin.sdk.value.Response
import de.nykon.bitcoin.sdk.value.TransactionType
import de.nykon.bitcoin.sdk.value.showMyOrders.MyOrder
import de.nykon.bitcoin.sdk.value.showMyOrders.ShowMyOrdersBody
import de.nykon.bitcoin.sdk.value.showOrderbook.Order
import de.nykon.bitcoin.sdk.value.showOrderbook.ShowOrderbookBody
import org.assertj.core.api.SoftAssertions
import org.assertj.core.util.Lists
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

    var showAccountInfo: ShowAccountInfo = mock()
    var showMyOrders: ShowMyOrders = mock()
    var showOrderbook: ShowOrderbook = mock()
    var deleteOrder: DeleteOrder = mock()
    var createOrder: CreateOrder = mock()

    @Test
    fun `deactive sell schedule once all coins are gone`() {
        // arrange
        var testee = Seller(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        // act
        testee.setResult(BigDecimal.ZERO, BigDecimal.valueOf(1000), 0)

        // assert
        SoftAssertions().apply {
            assertFalse(config.isActive)
        }
    }

    @Test
    fun `find lowest price`() {
        // arrange
        var testee = Seller(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        val orders = MyOrder.generate(5, 1100.0, 1500.0, TransactionType.SELL.name)
        val lowestPrice = MyOrder.generate(1, 1000.0, 1000.0, TransactionType.SELL.name)
        orders.add(lowestPrice.first())

        val showMyOrdersBody = ShowMyOrdersBody(200, Lists.emptyList(), orders)
        val response = Response(200, showMyOrdersBody)

        // act
        val myLowestPrice = testee.findMyLowestPrice(response)

        // assert
        SoftAssertions().apply {
            assertEquals(BigDecimal.valueOf(1000.0), myLowestPrice)
        }
    }

    @Test
    fun `calculate average price`() {
        var testee = Seller(config, showAccountInfo, showMyOrders,
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
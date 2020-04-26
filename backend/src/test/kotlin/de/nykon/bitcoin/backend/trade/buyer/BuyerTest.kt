package de.nykon.bitcoin.backend.trade.buyer

import com.nhaarman.mockito_kotlin.mock
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.bitcoinde.showAccountInfo.FidorReservation
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class BuyerTest {

    @Autowired
    private lateinit var config: BuyerSchedulConfig

    private var showAccountInfo: ShowAccountInfo = mock()
    private var showMyOrders: ShowMyOrders = mock()
    private var showOrderbook: ShowOrderbook = mock()
    private var deleteOrder: DeleteOrder = mock()
    private var createOrder: CreateOrder = mock()

    private val apiKey = System.getenv("bitcoin.api.key")!!
    private val apiSecret = System.getenv("bitcoin.api.secret")!!

    @Test
    fun `calculate amount of coins`() {
        // arrange
        val fidorReservation = FidorReservation(BigDecimal.valueOf(100), "none", BigDecimal.valueOf(100), "none")
        val currentPrice = BigDecimal.valueOf(5900)
        val expected = BigDecimal.valueOf(0.01694915)

        // act
        val testee = Buyer(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        val actual = testee.calculateAmountOfCoins(fidorReservation, currentPrice)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(expected, actual, "calculated amount of coins")
        }
    }

    @Test
    fun `delete open orders`() {
        // arrange
        val deleteOrder = DeleteOrder(apiKey, apiSecret)
        val showMyOrders = ShowMyOrders(apiKey, apiSecret)
        val myOrders = showMyOrders.all()

        // act
        val testee = Buyer(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        testee.deleteOrders(myOrders)

        val noOrdersLeft = showMyOrders.all()

        // assert
        SoftAssertions().apply {
            Assertions.assertTrue(noOrdersLeft.body.orders.isNullOrEmpty())
        }
    }
}
package de.nykon.bitcoin.backend.trade.buyer

import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
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

    @Autowired
    private lateinit var testee: Buyer

    @Autowired
    private lateinit var showMyOrders: ShowMyOrders

    @Test
    fun `calculate amount of coins`() {
        // arrange
        val fidorReservation = FidorReservation(BigDecimal.valueOf(100), "none", BigDecimal.valueOf(100), "none")
        val currentPrice = BigDecimal.valueOf(5900)
        val expected = BigDecimal.valueOf(0.01694915)

        val actual = testee.calculateAmountOfCoins(fidorReservation, currentPrice)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(expected, actual, "calculated amount of coins")
        }
    }

    @Test
    fun `delete open orders`() {
        // arrange
        val myOrders = showMyOrders.all()

        // act
        testee.deleteOrders(myOrders)

        val noOrdersLeft = showMyOrders.all()

        // assert
        SoftAssertions().apply {
            Assertions.assertTrue(noOrdersLeft.body.orders.isNullOrEmpty())
        }
    }
}
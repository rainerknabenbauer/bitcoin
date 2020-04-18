package de.nykon.bitcoin.backend.trading.schedules

import com.nhaarman.mockito_kotlin.mock
import de.nykon.bitcoin.backend.trading.schedules.config.BuyerSchedulConfig
import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.value.showAccountInfo.FidorReservation
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

    var showAccountInfo: ShowAccountInfo = mock()
    var showMyOrders: ShowMyOrders = mock()
    var showOrderbook: ShowOrderbook = mock()
    var deleteOrder: DeleteOrder = mock()
    var createOrder: CreateOrder = mock()

    @Test
    fun `calculate amount of coins`() {
        // arrange
        val fidorReservation = FidorReservation(BigDecimal.valueOf(100), "none", BigDecimal.valueOf(100), "none")
        val currentPrice = BigDecimal.valueOf(5900)
        val expected = BigDecimal.valueOf(0.01694915)

        // act
        var testee = Buyer(config, showAccountInfo, showMyOrders,
                showOrderbook, deleteOrder, createOrder)

        val actual = testee.calculateAmountOfCoins(fidorReservation, currentPrice)

        // assert
        SoftAssertions().apply {
            Assertions.assertEquals(expected, actual, "calculated amount of coins")
        }
    }
}
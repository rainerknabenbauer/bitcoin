package de.nykon.bitcoin.backend.trade.seller

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class SellerTest {

    @Autowired
    private lateinit var config: SellerSchedulConfig

    @Autowired
    private lateinit var testee: Seller

    @Test
    fun `deactive sell schedule once all coins are gone`() {
        // arrange

        // act
        testee.createOrder(BigDecimal.ZERO, BigDecimal.valueOf(1000), 0)

        // assert
        SoftAssertions().apply {
            assertFalse(config.isActive)
        }
    }

}
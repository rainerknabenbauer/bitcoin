package de.nykon.bitcoin.backend.trade.gatherer

import de.nykon.bitcoin.backend.trade.gatherer.value.Offer
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
internal class GathererTest {

    @Autowired
    private lateinit var gatherer: Gatherer

    @Test
    fun `calculate weighted average`() {
        // arrange
        val offer1 = Offer("offer1", BigDecimal.valueOf(8010), BigDecimal.valueOf(0.94007491))
        val offer2 = Offer("offer2", BigDecimal.valueOf(8059), BigDecimal.valueOf(0.60101592))
        val offer3 = Offer("offer3", BigDecimal.valueOf(8060), BigDecimal.valueOf(0.60101592))

        val list = listOf(offer1, offer2, offer3)

        // act
        val calculateWeightedAverage = gatherer.calculateWeightedAverage(list)

        // assert
        SoftAssertions().apply {
            assertEquals(BigDecimal.valueOf(8037.78), calculateWeightedAverage, "calculated weighted average")
        }
    }

    @Test
    fun `calculate weighted average ignores small offers`() {
        // arrange
        val offer1 = Offer("offer1", BigDecimal.valueOf(8010), BigDecimal.valueOf(0.94007491))
        val offer2 = Offer("offer2", BigDecimal.valueOf(8059), BigDecimal.valueOf(0.60101592))
        val offer3 = Offer("offer3", BigDecimal.valueOf(8060), BigDecimal.valueOf(0.60101592))
        val offer4 = Offer("offer3", BigDecimal.valueOf(8060), BigDecimal.valueOf(0.01))
        val offer5 = Offer("offer3", BigDecimal.valueOf(8060), BigDecimal.valueOf(0.02))

        val list = listOf(offer1, offer2, offer3, offer4, offer5)

        // act
        val calculateWeightedAverage = gatherer.calculateWeightedAverage(list)

        // assert
        SoftAssertions().apply {
            assertEquals(BigDecimal.valueOf(8037.78), calculateWeightedAverage, "calculated weighted average")
        }
    }

}
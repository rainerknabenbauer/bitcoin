package de.nykon.bitcoin.backend.domain

import de.nykon.bitcoin.backend.repository.value.PriceBatch
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest(classes = [])
internal class TraderServiceTest(val testee: TradingService) {

    val dataPoints = ArrayList<PriceBatch>()

    val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6000))
    val t1 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5980))
    val t2 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5960))
    val t3 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
    val t4 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5900))
    val t5 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5830))
    val t6 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5845))
    val t7 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5835))
    val t8 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5795))
    val t9 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5805))
    val t10 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5795))

    @Test
    fun `verify short prediction sticks to limits`() {
        dataPoints.add(t0)
        dataPoints.add(t1)
        dataPoints.add(t2)
        dataPoints.add(t3)
        dataPoints.add(t4)
        Assertions.assertEquals(5, dataPoints.size)

        val shortPrediction = testee.shortPrediction(dataPoints)

        Assertions.assertFalse(shortPrediction)
    }
}
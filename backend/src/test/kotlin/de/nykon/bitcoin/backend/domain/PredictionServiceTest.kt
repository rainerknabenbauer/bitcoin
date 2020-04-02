package de.nykon.bitcoin.backend.domain

import de.nykon.bitcoin.backend.repository.value.SupplyBatch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
internal class PredictionServiceTest {

    @Autowired
    lateinit var testee: PredictionService


    @Test
    fun `equities are SOLD on price drop`() {

        var top5 = ArrayList<SupplyBatch>()
        var top10 = ArrayList<SupplyBatch>()
        var top20 = ArrayList<SupplyBatch>()

        val t0 = SupplyBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6400))
        val t1 = SupplyBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(6430))
        val t2 = SupplyBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(6400))
        val t3 = SupplyBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(6350))
        val t4 = SupplyBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(6320))
        val t5 = SupplyBatch(5, 5, Collections.emptyList(), BigDecimal.valueOf(6275))
        val t6 = SupplyBatch(6, 5, Collections.emptyList(), BigDecimal.valueOf(6200))
        val t7 = SupplyBatch(7, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t8 = SupplyBatch(8, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t9 = SupplyBatch(9, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t10 = SupplyBatch(10, 5, Collections.emptyList(), BigDecimal.valueOf(6150))
        val t11 = SupplyBatch(11, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t12 = SupplyBatch(12, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t13 = SupplyBatch(13, 5, Collections.emptyList(), BigDecimal.valueOf(6150))
        val t14 = SupplyBatch(14, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t15 = SupplyBatch(15, 5, Collections.emptyList(), BigDecimal.valueOf(6110))
        val t16 = SupplyBatch(16, 5, Collections.emptyList(), BigDecimal.valueOf(6100))
        val t17 = SupplyBatch(17, 5, Collections.emptyList(), BigDecimal.valueOf(6075))
        val t18 = SupplyBatch(18, 5, Collections.emptyList(), BigDecimal.valueOf(6060))
        val t19 = SupplyBatch(19, 5, Collections.emptyList(), BigDecimal.valueOf(6075))

        top5.add(t15)
        top5.add(t16)
        top5.add(t17)
        top5.add(t18)
        top5.add(t19)

        top10.add(t10)
        top10.add(t11)
        top10.add(t12)
        top10.add(t13)
        top10.add(t14)
        top10.add(t15)
        top10.add(t16)
        top10.add(t17)
        top10.add(t18)
        top10.add(t19)

        top20.add(t0)
        top20.add(t1)
        top20.add(t2)
        top20.add(t3)
        top20.add(t4)
        top20.add(t5)
        top20.add(t6)
        top20.add(t7)
        top20.add(t8)
        top20.add(t9)
        top20.add(t10)
        top20.add(t11)
        top20.add(t12)
        top20.add(t13)
        top20.add(t14)
        top20.add(t15)
        top20.add(t16)
        top20.add(t17)
        top20.add(t18)
        top20.add(t19)

        assertTrue(testee.shortSellPrediction(top5))
        assertTrue(testee.mediumSellPrediction(top10))
        assertTrue(testee.longSellPrediction(top20))

    }

    @Test
    fun `DONT BUY on price drop`() {

        var top5 = ArrayList<SupplyBatch>()
        var top10 = ArrayList<SupplyBatch>()
        var top20 = ArrayList<SupplyBatch>()

        val t0 = SupplyBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6400))
        val t1 = SupplyBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(6430))
        val t2 = SupplyBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(6400))
        val t3 = SupplyBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(6350))
        val t4 = SupplyBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(6320))
        val t5 = SupplyBatch(5, 5, Collections.emptyList(), BigDecimal.valueOf(6275))
        val t6 = SupplyBatch(6, 5, Collections.emptyList(), BigDecimal.valueOf(6200))
        val t7 = SupplyBatch(7, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t8 = SupplyBatch(8, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t9 = SupplyBatch(9, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t10 = SupplyBatch(10, 5, Collections.emptyList(), BigDecimal.valueOf(6150))
        val t11 = SupplyBatch(11, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t12 = SupplyBatch(12, 5, Collections.emptyList(), BigDecimal.valueOf(6175))
        val t13 = SupplyBatch(13, 5, Collections.emptyList(), BigDecimal.valueOf(6150))
        val t14 = SupplyBatch(14, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t15 = SupplyBatch(15, 5, Collections.emptyList(), BigDecimal.valueOf(6110))
        val t16 = SupplyBatch(16, 5, Collections.emptyList(), BigDecimal.valueOf(6100))
        val t17 = SupplyBatch(17, 5, Collections.emptyList(), BigDecimal.valueOf(6075))
        val t18 = SupplyBatch(18, 5, Collections.emptyList(), BigDecimal.valueOf(6060))
        val t19 = SupplyBatch(19, 5, Collections.emptyList(), BigDecimal.valueOf(6075))

        top5.add(t15)
        top5.add(t16)
        top5.add(t17)
        top5.add(t18)
        top5.add(t19)

        top10.add(t10)
        top10.add(t11)
        top10.add(t12)
        top10.add(t13)
        top10.add(t14)
        top10.add(t15)
        top10.add(t16)
        top10.add(t17)
        top10.add(t18)
        top10.add(t19)

        top20.add(t0)
        top20.add(t1)
        top20.add(t2)
        top20.add(t3)
        top20.add(t4)
        top20.add(t5)
        top20.add(t6)
        top20.add(t7)
        top20.add(t8)
        top20.add(t9)
        top20.add(t10)
        top20.add(t11)
        top20.add(t12)
        top20.add(t13)
        top20.add(t14)
        top20.add(t15)
        top20.add(t16)
        top20.add(t17)
        top20.add(t18)
        top20.add(t19)

        assertFalse(testee.shortBuyPrediction(top5))
        assertFalse(testee.mediumBuyPrediction(top10))
        assertFalse(testee.longBuyPrediction(top20))

    }

}
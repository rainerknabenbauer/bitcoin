package de.nykon.bitcoin.backend.domain

import de.nykon.bitcoin.backend.repository.value.PriceBatch
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
    fun `shortBuyPrediction returns DONT BUY while flatline`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6000))
        val t1 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5980))
        val t2 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5960))
        val t3 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5900))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertFalse(testee.shortBuyPrediction(top5))
    }

    @Test
    fun `shortBuyPrediction returns BUY for price drop`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6000))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5950))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5900))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5800))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertTrue(testee.shortBuyPrediction(top5))
    }

    @Test
    fun `shortBuyPrediction returns BUY for price raise`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5950))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5900))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(6000))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertTrue(testee.shortBuyPrediction(top5))
    }

    @Test
    fun `mediumBuyPrediction returns DONT BUY while flatline`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6000))
        val t1 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5980))
        val t2 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5960))
        val t3 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5900))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertFalse(testee.mediumBuyPrediction(top5))
    }

    @Test
    fun `mediumBuyPrediction returns BUY for price drop`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(6000))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5950))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5900))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5750))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertTrue(testee.mediumBuyPrediction(top5))
    }

    @Test
    fun `mediumBuyPrediction returns BUY for price raise`() {

        var top5 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5875))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5951))

        top5.add(t0)
        top5.add(t1)
        top5.add(t2)
        top5.add(t3)
        top5.add(t4)
        assertEquals(5, top5.size)

        assertTrue(testee.mediumBuyPrediction(top5))
    }

    @Test
    fun `longBuyPrediction returns DONT BUY while flatline`() {

        var top10 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5875))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5951))
        val t5 = PriceBatch(5, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t6 = PriceBatch(6, 5, Collections.emptyList(), BigDecimal.valueOf(5875))
        val t7 = PriceBatch(7, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t8 = PriceBatch(8, 5, Collections.emptyList(), BigDecimal.valueOf(5900))
        val t9 = PriceBatch(9, 5, Collections.emptyList(), BigDecimal.valueOf(5875))

        top10.add(t0)
        top10.add(t1)
        top10.add(t2)
        top10.add(t3)
        top10.add(t4)
        top10.add(t5)
        top10.add(t6)
        top10.add(t7)
        top10.add(t8)
        top10.add(t9)
        assertEquals(10, top10.size)

        assertFalse(testee.longBuyPrediction(top10))
    }

    @Test
    fun `longBuyPrediction returns BUY for price drop`() {

        //TODO Rethink this test case. May need to be replaced with long analysis instead of fixed value

        var top10 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5875))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5951))
        val t5 = PriceBatch(5, 5, Collections.emptyList(), BigDecimal.valueOf(6001))
        val t6 = PriceBatch(6, 5, Collections.emptyList(), BigDecimal.valueOf(6141))
        val t7 = PriceBatch(7, 5, Collections.emptyList(), BigDecimal.valueOf(6100))
        val t8 = PriceBatch(8, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t9 = PriceBatch(9, 5, Collections.emptyList(), BigDecimal.valueOf(6175))

        top10.add(t0)
        top10.add(t1)
        top10.add(t2)
        top10.add(t3)
        top10.add(t4)
        top10.add(t5)
        top10.add(t6)
        top10.add(t7)
        top10.add(t8)
        top10.add(t9)
        assertEquals(10, top10.size)

        assertTrue(testee.longBuyPrediction(top10))
    }

    @Test
    fun `longBuyPrediction returns BUY for price raise`() {

        var top10 = ArrayList<PriceBatch>()

        val t0 = PriceBatch(0, 5, Collections.emptyList(), BigDecimal.valueOf(5800))
        val t1 = PriceBatch(1, 5, Collections.emptyList(), BigDecimal.valueOf(5850))
        val t2 = PriceBatch(2, 5, Collections.emptyList(), BigDecimal.valueOf(5875))
        val t3 = PriceBatch(3, 5, Collections.emptyList(), BigDecimal.valueOf(5920))
        val t4 = PriceBatch(4, 5, Collections.emptyList(), BigDecimal.valueOf(5951))
        val t5 = PriceBatch(5, 5, Collections.emptyList(), BigDecimal.valueOf(6001))
        val t6 = PriceBatch(6, 5, Collections.emptyList(), BigDecimal.valueOf(6141))
        val t7 = PriceBatch(7, 5, Collections.emptyList(), BigDecimal.valueOf(6100))
        val t8 = PriceBatch(8, 5, Collections.emptyList(), BigDecimal.valueOf(6125))
        val t9 = PriceBatch(9, 5, Collections.emptyList(), BigDecimal.valueOf(6175))

        top10.add(t0)
        top10.add(t1)
        top10.add(t2)
        top10.add(t3)
        top10.add(t4)
        top10.add(t5)
        top10.add(t6)
        top10.add(t7)
        top10.add(t8)
        top10.add(t9)
        assertEquals(10, top10.size)

        assertTrue(testee.longBuyPrediction(top10))
    }


}
package de.nykon.bitcoin.sdk.value.showMyOrders

import java.math.BigDecimal
import kotlin.random.Random

data class MyOrder(
        val created_at: String,
        val end_datetime: String,
        val is_external_wallet_order: Boolean,
        val max_amount_currency_to_trade: BigDecimal,
        val max_volume_currency_to_pay: BigDecimal,
        val min_amount_currency_to_trade: BigDecimal,
        val min_volume_currency_to_pay: BigDecimal,
        val new_order_for_remaining_amount: Boolean,
        val order_id: String,
        val order_requirements: OrderRequirements?,
        val price: BigDecimal,
        val state: Int,
        val trading_pair: String,
        val type: String
) {
    companion object {
        fun generate(size: Int, priceRangeStart: Double, priceRangeEnd: Double, type: String): ArrayList<MyOrder> {

            val list = ArrayList<MyOrder>()
            val valueSet: List<Char> = ('A'..'Z') + ('0'..'9')

            for (i in 0 until size) {

                val orderId = generateOrderId(valueSet)

                list.add(
                        MyOrder(
                                "",
                                "",
                                false,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                false,
                                orderId,
                                null,
                                generatePrice(priceRangeStart, priceRangeEnd),
                                0,
                                "btceur",
                                type
                        )
                )
            }
            return list
        }

        private fun generatePrice(priceRangeStart: Double, priceRangeEnd: Double): BigDecimal {
            return if (priceRangeStart == priceRangeEnd) {
                BigDecimal.valueOf(priceRangeStart)
            } else {
                BigDecimal.valueOf(Random.nextDouble(priceRangeStart, priceRangeEnd))
            }
        }

        private fun generateOrderId(valueSet: List<Char>): String {
            return (1..6)
                    .map { _ -> Random.nextInt(0, valueSet.size) }
                    .map(valueSet::get)
                    .joinToString("")
        }
    }
}

package de.nykon.bitcoin.sdk.value.bitcoinde.showOrderbook

import java.math.BigDecimal
import kotlin.random.Random


data class Order(
        val is_external_wallet_order: Boolean,
        val max_amount_currency_to_trade: BigDecimal,
        val max_volume_currency_to_pay: BigDecimal,
        val min_amount_currency_to_trade: BigDecimal,
        val min_volume_currency_to_pay: BigDecimal,
        val order_id: String,
        val order_requirements: OrderRequirements?,
        val order_requirements_fullfilled: Boolean,
        val price: BigDecimal,
        val trading_pair: String,
        val trading_partner_information: TradingPartnerInformation,
        val type: String
) {

    companion object {
        fun generate(size: Int, priceRangeStart: Double, priceRangeEnd: Double, type: String): ArrayList<Order> {

            val list = ArrayList<Order>()
            val valueSet: List<Char> = ('A'..'Z') + ('0'..'9')

            for (i in 0 until size) {

                val orderId = generateOrderId(valueSet)

                list.add(
                        Order(
                                false,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                orderId,
                                null,
                                false,
                                BigDecimal.valueOf(Random.nextDouble(priceRangeStart, priceRangeEnd)),
                                "btceur",
                                TradingPartnerInformation(0, "", "", false, 1, "", "", ""),
                                type
                        )
                )
            }
            return list
        }

        private fun generateOrderId(valueSet: List<Char>): String {
            return (1..6)
                    .map { _ -> Random.nextInt(0, valueSet.size) }
                    .map(valueSet::get)
                    .joinToString("")
        }
    }

}
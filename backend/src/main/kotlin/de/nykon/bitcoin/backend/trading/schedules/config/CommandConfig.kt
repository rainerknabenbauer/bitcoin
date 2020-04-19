package de.nykon.bitcoin.backend.trading.schedules.config

import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.ShowPublicTradeHistory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CommandConfig {

    @Value("\${bitcoin.apiKey}")
    lateinit var apiKey: String

    @Value("\${bitcoin.apiSecret}")
    lateinit var apiSecret: String

    @Bean
    open fun showAccountInfo(): ShowAccountInfo {
        return ShowAccountInfo(apiKey, apiSecret)
    }

    @Bean
    open fun showMyOrders(): ShowMyOrders {
        return ShowMyOrders(apiKey, apiSecret)
    }

    @Bean
    open fun showOrderbook(): ShowOrderbook {
        return ShowOrderbook(apiKey, apiSecret)
    }

    @Bean
    open fun showPublicTradeHistory(): ShowPublicTradeHistory {
        return ShowPublicTradeHistory(apiKey, apiSecret)
    }

    @Bean
    open fun deleteOrder(): DeleteOrder {
        return DeleteOrder(apiKey, apiSecret)
    }

    @Bean
    open fun createOrder(): CreateOrder {
        return CreateOrder(apiKey, apiSecret)
    }
}
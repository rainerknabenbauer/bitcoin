package de.nykon.bitcoin.backend

import de.nykon.bitcoin.sdk.bitcoinDe.CreateOrder
import de.nykon.bitcoin.sdk.bitcoinDe.DeleteOrder
import de.nykon.bitcoin.sdk.bitcoinDe.ShowAccountInfo
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyOrders
import de.nykon.bitcoin.sdk.bitcoinDe.ShowMyTrades
import de.nykon.bitcoin.sdk.bitcoinDe.ShowOrderbook
import de.nykon.bitcoin.sdk.bitcoinDe.ShowPublicTradeHistory
import de.nykon.bitcoin.sdk.cryptowatch.ShowKrakenSummary
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configures the beans from the SDK and provides them to the Spring container.
 */
@Configuration
open class SdkConfig {

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

    @Bean
    open fun showMyTrades(): ShowMyTrades {
        return ShowMyTrades(apiKey, apiSecret)
    }

    @Bean
    open fun showKrakenSummary(): ShowKrakenSummary {
        return ShowKrakenSummary()
    }
}
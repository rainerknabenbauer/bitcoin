package de.nykon.bitcoin.backend.trading.schedules.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SellerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 5

    @Bean
    open fun sellConfiguration(): Boolean {
        return isActive
    }


}
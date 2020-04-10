package de.nykon.bitcoin.backend.trading.schedules.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SellSchedulerConfiguration {

    var isSellActive = false

    @Bean
    open fun sellConfiguration(): Boolean {
        return isSellActive
    }


}
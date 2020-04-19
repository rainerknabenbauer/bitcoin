package de.nykon.bitcoin.backend.trading.schedules.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class BuyerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 2

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false
}
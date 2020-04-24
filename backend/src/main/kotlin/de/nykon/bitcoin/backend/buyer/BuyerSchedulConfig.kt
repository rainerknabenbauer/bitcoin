package de.nykon.bitcoin.backend.buyer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class BuyerSchedulConfig {

    var isActive = false
    val consideredOrderSize = 3

    @Value(value = "\${api.isLive}")
    var isLiveChange: Boolean = false
}
package de.nykon.bitcoin.backend.trade.gatherer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class GathererConfig {

    @Value(value = "\${bitcoin.trading.selling.active}")
    var isActive: Boolean = false

}
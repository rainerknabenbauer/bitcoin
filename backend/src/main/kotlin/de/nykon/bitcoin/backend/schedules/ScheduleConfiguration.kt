package de.nykon.bitcoin.backend.schedules

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ScheduleConfiguration {

    var isActive = false
    var amount

    @Bean
    open fun sellConfiguration(): Boolean {
        return isActive
    }






}
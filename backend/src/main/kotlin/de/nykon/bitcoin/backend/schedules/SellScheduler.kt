package de.nykon.bitcoin.backend.schedules

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SellScheduler() {

    @Scheduled(cron = "0 * * * * *")
    fun minuteMen() {
        println(LocalDateTime.now().toString() + "a minute has passed")
    }

}
package de.nykon.bitcoin.backend.trade

import de.nykon.bitcoin.backend.trade.value.CompletedTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MyTradeRepository : MongoRepository<CompletedTrade, String> {

    fun findTopByDateTime(now: LocalDateTime): CompletedTrade

}
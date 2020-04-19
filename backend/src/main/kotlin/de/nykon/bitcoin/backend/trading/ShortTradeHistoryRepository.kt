package de.nykon.bitcoin.backend.trading

import de.nykon.bitcoin.backend.trading.value.ShortTermTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Stores the last 24h hours of public trades in a rotating schema.
 */
@Repository
interface ShortTradeHistoryRepository : MongoRepository<ShortTermTrade, String> {

}
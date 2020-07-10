package de.nykon.bitcoin.backend.trade.gatherer.repository

import de.nykon.bitcoin.backend.trade.gatherer.value.ShortTermTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Stores the last 24h hours of public trades in a rotating schema.
 */
@Repository
interface ShortTradeHistoryRepository : MongoRepository<ShortTermTrade, String> {

}
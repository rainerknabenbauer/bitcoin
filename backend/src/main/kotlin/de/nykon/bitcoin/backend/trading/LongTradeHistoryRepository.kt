package de.nykon.bitcoin.backend.trading

import de.nykon.bitcoin.backend.trading.value.LongTermTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Total public trades that were recorded.
 */
@Repository
interface LongTradeHistoryRepository : MongoRepository<LongTermTrade, String> {

}
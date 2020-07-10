package de.nykon.bitcoin.backend.trade.gatherer.repository

import de.nykon.bitcoin.backend.trade.gatherer.value.LongTermTrade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Total public trades that were recorded.
 */
@Repository
interface LongTradeHistoryRepository : MongoRepository<LongTermTrade, String> {

}
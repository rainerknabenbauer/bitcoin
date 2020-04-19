package de.nykon.bitcoin.backend.trading

import de.nykon.bitcoin.backend.trading.value.SellOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current SELL offers.
 */
@Repository
interface SellOrderbookRepository : MongoRepository<SellOrderbook, String> {

}
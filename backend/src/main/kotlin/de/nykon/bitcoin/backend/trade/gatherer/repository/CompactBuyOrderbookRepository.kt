package de.nykon.bitcoin.backend.trade.gatherer.repository

import de.nykon.bitcoin.backend.trade.gatherer.value.CompactBuyOrderbook
import de.nykon.bitcoin.backend.trade.gatherer.value.CompactSellOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current BUY offers.
 */
@Repository
interface CompactBuyOrderbookRepository : MongoRepository<CompactBuyOrderbook, String> {

    fun findFirstByOrderByDateTimeDesc(): CompactSellOrderbook

}
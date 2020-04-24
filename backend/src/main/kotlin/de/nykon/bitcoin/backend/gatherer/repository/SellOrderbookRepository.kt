package de.nykon.bitcoin.backend.gatherer.repository

import de.nykon.bitcoin.backend.gatherer.value.SellOrderbook
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Records current SELL offers.
 */
@Repository
interface SellOrderbookRepository : MongoRepository<SellOrderbook, String> {

}
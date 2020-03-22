package de.nykon.bitcoin.backend.repository

import de.nykon.bitcoin.backend.repository.value.PriceBatch
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BitcoinRepository : MongoRepository<PriceBatch, Int> {

    fun findTop5ByOrderByTimestampDesc(): List<PriceBatch>

    fun findTop10ByOrderByTimestampDesc(): List<PriceBatch>

    fun findTop20ByOrderByTimestampDesc(): List<PriceBatch>

}
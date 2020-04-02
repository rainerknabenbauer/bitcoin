package de.nykon.bitcoin.backend.repository

import de.nykon.bitcoin.backend.repository.value.SupplyBatch
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BitcoinRepository : MongoRepository<SupplyBatch, Int> {

    fun findTop5ByOrderByTimestampDesc(): List<SupplyBatch>

    fun findTop10ByOrderByTimestampDesc(): List<SupplyBatch>

    fun findTop20ByOrderByTimestampDesc(): List<SupplyBatch>

}
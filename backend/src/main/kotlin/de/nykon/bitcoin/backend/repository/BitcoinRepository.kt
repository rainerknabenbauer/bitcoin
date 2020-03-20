package de.nykon.bitcoin.backend.repository

import de.nykon.bitcoin.backend.repository.value.PriceBatch
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BitcoinRepository : ReactiveCrudRepository<PriceBatch, Int>{

    fun findTop5ByOrderByTimestampDesc(): List<PriceBatch>

    fun findTop10ByOrderByTimestampDesc(): List<PriceBatch>

    fun findTop20ByOrderByTimestampDesc(): List<PriceBatch>

}
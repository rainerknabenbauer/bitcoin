package de.nykon.bitcoin.client.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MongoInitializer {

    @Autowired
    lateinit var mongoOperations: ReactiveMongoOperations

    @PostConstruct
    fun initData() {
        mongoOperations.collectionExists("bitcoin").subscribe {
            if (it) {
                println("bitcoin collection already exists.")
            } else {
                mongoOperations.createCollection("bitcoin").subscribe()
                {
                    println("bitcoin collection created.")
                }
            }
        }
    }
}
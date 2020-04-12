package de.nykon.bitcoin.backend.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MongoInitializer {

    @Autowired
    lateinit var mongoOperations: ReactiveMongoOperations

    @PostConstruct
    fun initData() {
        mongoOperations.collectionExists("users").subscribe {
            if (it) {
                println("users collection already exists.")
            } else {
                mongoOperations.createCollection("users").subscribe()
                {
                    println("users collection created.")
                }
            }
        }
    }
}
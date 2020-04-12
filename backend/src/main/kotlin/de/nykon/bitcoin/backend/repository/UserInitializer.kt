package de.nykon.bitcoin.backend.repository

import de.nykon.bitcoin.backend.repository.value.HashUtils
import de.nykon.bitcoin.backend.repository.value.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class UserInitializer(private var userRepository: UserRepository) {

    @Value("\${default.user}")
    private lateinit var defaultUser: String

    @Value("\${default.password}")
    private lateinit var defaultPassword: String

    @PostConstruct
    fun loadData() {

        val findUserByName = userRepository.findUserByName(defaultUser)

        if (findUserByName == null) {
            val defaultUser = User(
                    UUID.randomUUID(),
                    defaultUser,
                    HashUtils.sha256(defaultPassword),
                    LocalDateTime.now(),
                    null
            )

            userRepository.save(defaultUser)
        }
    }

}
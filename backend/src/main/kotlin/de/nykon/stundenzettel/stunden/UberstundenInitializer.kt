package de.nykon.stundenzettel.stunden

import de.nykon.stundenzettel.stunden.value.Uberstunden
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
import javax.annotation.PostConstruct

@Component
class UberstundenInitializer(private var uberstundenRepository: UberstundenRepository) {

    @PostConstruct
    fun loadData() {

        val stundenzettel = uberstundenRepository.findAll()

        if (stundenzettel.isEmpty()) {
            val uberstunden = Uberstunden(
                    UUID.randomUUID(),
                    LocalDate.now(),
                    0.0,
                    "Initialization"
            )

            uberstundenRepository.save(uberstunden)
        }
    }
}
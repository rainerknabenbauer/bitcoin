package de.nykon.bitcoin

import de.nykon.bitcoin.value.Customer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
open class BitcoinApplication {

	companion object {
		var initialCustomers = arrayOf(
				Customer(1, "Bob"),
				Customer(2, "Alice"),
				Customer(3, "Malice")
		)
	}

	@Bean
	open fun customers() = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))
}

fun main(args: Array<String>) {
	runApplication<BitcoinApplication>(*args)
}



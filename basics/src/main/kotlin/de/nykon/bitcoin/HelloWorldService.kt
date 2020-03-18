package de.nykon.bitcoin

import de.nykon.bitcoin.value.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class HelloWorldService {

    @Value("\${helloworld.message}")
    private lateinit var text: String

    @Autowired
    private lateinit var customers: ConcurrentHashMap<Int, Customer>

    fun getHello(name: String) = "hello $name"

    fun getMessage() = "Message is: $text"

    fun setCustomer(customer: Customer) : Customer? = customers.putIfAbsent(customer.id, customer)

    fun getCustomer(id: Int) : Customer? = customers[id]

    fun getCustomers() = customers.values.toList()
}

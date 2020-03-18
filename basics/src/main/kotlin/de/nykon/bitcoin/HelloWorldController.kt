package de.nykon.bitcoin

import de.nykon.bitcoin.value.Customer
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class HelloWorldController(val helloWorldService: HelloWorldService) {

    @RequestMapping(value = ["/user/{name}"], method = [RequestMethod.GET])
    @ResponseBody
    fun hello(@PathVariable name: String) = helloWorldService.getHello(name)

    @GetMapping("")
    @ResponseBody
    fun hellos() = helloWorldService.getMessage()

    @GetMapping("/customer/{id}")
    @ResponseBody
    fun getCustomer(@PathVariable id: Int) : Customer? = helloWorldService.getCustomer(id)

    @GetMapping("/customer")
    @ResponseBody
    fun getCustomers() = helloWorldService.getCustomers()

    @PostMapping("/customer")
    @ResponseBody
    fun setCustomer(@RequestBody customer : Customer) : Customer {
        return helloWorldService.setCustomer(customer) ?: customer
    }


}
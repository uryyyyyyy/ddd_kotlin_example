package ddd_example.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "ddd_example.business_logic",
        "ddd_example.web",
    ],
)
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

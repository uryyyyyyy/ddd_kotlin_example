package ddd_example.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "ddd_example.domain",
        "ddd_example.application",
    ],
)
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

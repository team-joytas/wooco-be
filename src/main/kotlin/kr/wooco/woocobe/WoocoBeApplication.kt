package kr.wooco.woocobe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class WoocoBeApplication

fun main(args: Array<String>) {
    runApplication<WoocoBeApplication>(*args)
}

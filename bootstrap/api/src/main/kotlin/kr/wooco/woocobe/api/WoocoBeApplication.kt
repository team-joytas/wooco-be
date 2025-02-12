package kr.wooco.woocobe.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "kr.wooco.woocobe.common",
        "kr.wooco.woocobe.api",
        "kr.wooco.woocobe.core",
        "kr.wooco.woocobe.jwt",
        "kr.wooco.woocobe.aws",
        "kr.wooco.woocobe.mysql",
        "kr.wooco.woocobe.rest",
        "kr.wooco.woocobe.redis",
    ],
)
@ConfigurationPropertiesScan
class WoocoBeApplication

fun main(args: Array<String>) {
    runApplication<WoocoBeApplication>(*args)
}

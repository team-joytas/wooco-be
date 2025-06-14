package kr.wooco.woocobe.api

import kr.wooco.woocobe.aws.common.config.AwsConfig
import kr.wooco.woocobe.core.common.config.CoreConfig
import kr.wooco.woocobe.core.common.config.SchedulingConfig
import kr.wooco.woocobe.fcm.common.config.FcmConfig
import kr.wooco.woocobe.mysql.common.config.MysqlConfig
import kr.wooco.woocobe.redis.common.config.RedisConfig
import kr.wooco.woocobe.rest.common.config.RestConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
    value = [
        CoreConfig::class,
        AwsConfig::class,
        MysqlConfig::class,
        RedisConfig::class,
        RestConfig::class,
        FcmConfig::class,
        SchedulingConfig::class,
    ],
)
@SpringBootApplication
@ConfigurationPropertiesScan
class WoocoBeApplication

fun main(args: Array<String>) {
    runApplication<WoocoBeApplication>(*args)
}

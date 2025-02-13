package kr.wooco.woocobe.redis.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.redis"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.redis"])
class RedisConfig

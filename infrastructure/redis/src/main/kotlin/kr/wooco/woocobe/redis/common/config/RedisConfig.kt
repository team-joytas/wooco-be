package kr.wooco.woocobe.redis.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.redis"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.redis"])
@EnableRedisRepositories(basePackages = ["kr.wooco.woocobe.redis.*.repository"])
class RedisConfig

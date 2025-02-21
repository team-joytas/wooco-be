package kr.wooco.woocobe.core.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@ComponentScan(basePackages = ["kr.wooco.woocobe.core"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.core"])
class SchedulingConfig

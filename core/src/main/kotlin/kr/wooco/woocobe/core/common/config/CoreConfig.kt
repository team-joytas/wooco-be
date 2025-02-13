package kr.wooco.woocobe.core.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.core"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.core"])
class CoreConfig

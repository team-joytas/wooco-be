package kr.wooco.woocobe.jwt.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.jwt"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.jwt"])
class JwtConfig

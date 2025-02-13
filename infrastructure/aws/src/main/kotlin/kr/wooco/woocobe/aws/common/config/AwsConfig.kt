package kr.wooco.woocobe.aws.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.aws"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.aws"])
class AwsConfig

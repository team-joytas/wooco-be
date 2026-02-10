package kr.wooco.woocobe.mysql.common.config

import kr.wooco.woocobe.mysql.common.advice.CustomJpaRepositoryFactoryBean
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

private const val MYSQL_BASE_PACKAGE = "kr.wooco.woocobe.mysql"

@Configuration
@EntityScan(basePackages = [MYSQL_BASE_PACKAGE])
@ComponentScan(basePackages = [MYSQL_BASE_PACKAGE])
@EnableJpaRepositories(
    basePackages = [MYSQL_BASE_PACKAGE],
    repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean::class,
)
@ConfigurationPropertiesScan(basePackages = [MYSQL_BASE_PACKAGE])
class MysqlConfig

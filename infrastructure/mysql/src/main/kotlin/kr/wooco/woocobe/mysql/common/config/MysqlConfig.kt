package kr.wooco.woocobe.mysql.common.config

import kr.wooco.woocobe.mysql.common.advice.CustomJpaRepositoryFactoryBean
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["kr.wooco.woocobe.mysql"])
@ComponentScan(basePackages = ["kr.wooco.woocobe.mysql"])
@EnableJpaRepositories(
    basePackages = ["kr.wooco.woocobe.mysql"],
    repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean::class,
)
class MysqlConfig

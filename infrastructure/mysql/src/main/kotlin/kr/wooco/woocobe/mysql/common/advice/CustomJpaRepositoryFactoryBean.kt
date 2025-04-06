package kr.wooco.woocobe.mysql.common.advice

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
import org.springframework.data.repository.Repository
import org.springframework.data.repository.core.support.RepositoryFactorySupport

class CustomJpaRepositoryFactoryBean<T : Repository<S, ID>, S, ID>(
    repositoryInterface: Class<out T>,
) : JpaRepositoryFactoryBean<T, S, ID>(repositoryInterface),
    ApplicationEventPublisherAware {
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

    override fun doCreateRepositoryFactory(): RepositoryFactorySupport {
        val repositoryFactorySupport = super.doCreateRepositoryFactory()

        addEventPublishMethodInterceptor(repositoryFactorySupport)

        return repositoryFactorySupport
    }

    private fun addEventPublishMethodInterceptor(repositoryFactorySupport: RepositoryFactorySupport) =
        repositoryFactorySupport.addRepositoryProxyPostProcessor { factory, _ ->
            factory.addAdvice(EventPublishingMethodInterceptor(applicationEventPublisher))
        }
}

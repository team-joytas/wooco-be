package kr.wooco.woocobe.api.common.utils

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
object SpringContextLoader : ApplicationContextAware {
    private lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    fun <T> getBean(beanClass: Class<T>): T = context.getBean(beanClass)
}

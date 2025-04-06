package kr.wooco.woocobe.api.common.config

import org.slf4j.MDC
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.time.Duration
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig(
    private val asyncProperties: AsyncProperties,
) : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            maxPoolSize = asyncProperties.maxSize
            corePoolSize = asyncProperties.coreSize
            queueCapacity = asyncProperties.queueCapacity
            setThreadNamePrefix(ASYNC_THREAD_PREFIX)
            setWaitForTasksToCompleteOnShutdown(true)
            setAwaitTerminationSeconds(asyncProperties.awaitDuration.toSeconds().toInt())
            setTaskDecorator(MdcDecorator)
            initialize()
        }

    companion object {
        private const val ASYNC_THREAD_PREFIX = "async-task-"

        object MdcDecorator : TaskDecorator {
            override fun decorate(runnable: Runnable): Runnable {
                val contextMap = MDC.getCopyOfContextMap()
                return Runnable {
                    try {
                        contextMap?.let(MDC::setContextMap)
                        runnable.run()
                    } finally {
                        MDC.clear()
                    }
                }
            }
        }

        @ConfigurationProperties(prefix = "spring.async-task-pool")
        data class AsyncProperties(
            val maxSize: Int,
            val coreSize: Int,
            val queueCapacity: Int,
            val awaitDuration: Duration,
        )
    }
}

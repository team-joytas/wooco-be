package kr.wooco.woocobe.api.common.config

import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${spring.async-task-pool.max-size}") private val asyncThreadMaxPoolSize: Int,
    @Value("\${spring.async-task-pool.core-size}") private val asyncThreadCorePoolSize: Int,
    @Value("\${spring.async-task-pool.queue-capacity}") private val asyncThreadQueueCapacity: Int,
    @Value("\${spring.async-task-pool.await-duration}") private val asyncAwaitTerminationDuration: Duration,
) : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            maxPoolSize = asyncThreadMaxPoolSize
            corePoolSize = asyncThreadCorePoolSize
            queueCapacity = asyncThreadQueueCapacity
            setThreadNamePrefix(ASYNC_THREAD_PREFIX)
            setWaitForTasksToCompleteOnShutdown(true)
            setAwaitTerminationSeconds(asyncAwaitTerminationDuration.toSeconds().toInt())
            setTaskDecorator(MdcDecorator)
            initialize()
        }

    companion object {
        private const val ASYNC_THREAD_PREFIX = "async-task-"

        object MdcDecorator : TaskDecorator {
            override fun decorate(runnable: Runnable): Runnable =
                Runnable {
                    try {
                        MDC.getCopyOfContextMap()?.let(MDC::setContextMap)
                        runnable.run()
                    } finally {
                        MDC.clear()
                    }
                }
        }
    }
}

package kr.wooco.woocobe.api.common.config

import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig(
    @Value("\${spring.threads.async.pool.max}") private val asyncThreadMaxPoolSize: Int,
    @Value("\${spring.threads.async.pool.core}") private val asyncThreadCorePoolSize: Int,
) : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            maxPoolSize = asyncThreadMaxPoolSize
            corePoolSize = asyncThreadCorePoolSize
            setThreadNamePrefix(ASYNC_THREAD_PREFIX)
            setTaskDecorator(MdcDecorator)
            initialize()
        }

    companion object {
        private const val ASYNC_THREAD_PREFIX = "async-thread-"

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

// package kr.wooco.woocobe.api.support
//
// import io.kotest.core.listeners.TestListener
// import io.kotest.core.test.TestCase
// import io.kotest.core.test.TestResult
// import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.data.redis.connection.RedisConnectionFactory
// import org.springframework.test.context.TestContextManager
//
// @SpringBootTest
// @TestEnvironment
// class RedisCleaner : TestListener {
//    lateinit var connectionFactory: RedisConnectionFactory
//
//    override suspend fun afterContainer(
//        testCase: TestCase,
//        result: TestResult,
//    ) {
//        val testContextManager = TestContextManager(this::class.java)
//        testContextManager.prepareTestInstance(this)
//        connectionFactory =
//            testContextManager.testContext.applicationContext
//                .getBean(RedisConnectionFactory::class.java)
//        connectionFactory.connection.use { connection ->
//            connection.serverCommands().flushDb()
//        }
//    }
// }

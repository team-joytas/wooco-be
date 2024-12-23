package kr.wooco.woocobe.support

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContextManager
import java.sql.DatabaseMetaData
import java.sql.SQLException

@SpringBootTest
@TestEnvironment
class MysqlCleaner : TestListener {
    lateinit var jdbcTemplate: JdbcTemplate

    override suspend fun afterContainer(
        testCase: TestCase,
        result: TestResult,
    ) {
        val testContextManager = TestContextManager(this::class.java)
        testContextManager.prepareTestInstance(this)
        jdbcTemplate = testContextManager.testContext.applicationContext.getBean(JdbcTemplate::class.java)
        val tables = getAllTablesWithOutExcludeTables(jdbcTemplate)
        truncateAll(tables = tables, jdbcTemplate = jdbcTemplate)
    }

    private fun getAllTablesWithOutExcludeTables(jdbcTemplate: JdbcTemplate): List<String> {
        try {
            jdbcTemplate.dataSource?.connection!!.use { connection ->
                val metaData: DatabaseMetaData = connection.metaData
                val tables = metaData
                    .getTables(null, null, null, arrayOf("TABLE"))
                    .use { resultSet ->
                        generateSequence {
                            resultSet.takeIf { it.next() }?.getString("TABLE_NAME")
                        }.toList()
                    }
                return tables.filter { EXCLUDED_TABLES.contains(it).not() }
            }
        } catch (e: SQLException) {
            throw IllegalStateException(e)
        }
    }

    private fun truncateAll(
        tables: List<String>,
        jdbcTemplate: JdbcTemplate,
    ) {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0")
        tables.forEach { table -> jdbcTemplate.execute("TRUNCATE TABLE $table") }
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1")
    }

    companion object {
        private val EXCLUDED_TABLES = setOf(
            "flyway_schema_history",
        )
    }
}

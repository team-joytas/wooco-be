package kr.wooco.woocobe.mysql.common.warmup

import jakarta.persistence.EntityManager
import kr.wooco.woocobe.common.warmup.AbstractWarmup
import org.springframework.stereotype.Component

/**
 * Database 연결 및 JPA/Hibernate Warmup
 *
 * 커넥션 풀 초기화, Hibernate 메타데이터 로딩, 쿼리 파싱 등을 warmup합니다.
 */
@Component
class MysqlWarmup(
    private val entityManager: EntityManager,
    private val properties: MysqlWarmupProperties,
) : AbstractWarmup() {
    override val name: String = NAME
    override val enabled: Boolean get() = properties.enabled
    override val iterations: Int get() = properties.iterations

    override fun doWarmup() {
        // 커넥션 풀 warmup
        entityManager.createNativeQuery("SELECT 1").singleResult

        // Hibernate 메타데이터 및 쿼리 파서 warmup
        entityManager.metamodel.entities.take(ENTITY_LIMIT).forEach { entityType ->
            runCatching {
                val cb = entityManager.criteriaBuilder
                val query = cb.createQuery(entityType.javaType)
                query.from(entityType.javaType)
                entityManager.createQuery(query).setMaxResults(1).resultList
            }
        }

        // EntityManager 캐시 정리
        entityManager.clear()
    }

    companion object {
        const val NAME = "mysql"
        private const val ENTITY_LIMIT = 5
    }
}

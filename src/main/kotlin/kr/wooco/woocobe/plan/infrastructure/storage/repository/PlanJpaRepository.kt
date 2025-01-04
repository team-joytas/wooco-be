package kr.wooco.woocobe.plan.infrastructure.storage.repository

import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaRepository : JpaRepository<PlanJpaEntity, Long> {
    fun findAllByUserId(userId: Long): List<PlanJpaEntity>
}

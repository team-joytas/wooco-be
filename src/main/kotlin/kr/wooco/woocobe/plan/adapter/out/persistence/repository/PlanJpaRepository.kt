package kr.wooco.woocobe.plan.adapter.out.persistence.repository

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaRepository : JpaRepository<PlanJpaEntity, Long> {
    fun findAllByUserId(userId: Long): List<PlanJpaEntity>
}

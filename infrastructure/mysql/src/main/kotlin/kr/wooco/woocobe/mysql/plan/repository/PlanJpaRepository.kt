package kr.wooco.woocobe.mysql.plan.repository

import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaRepository : JpaRepository<PlanJpaEntity, Long> {
    fun findAllByUserId(userId: Long): List<PlanJpaEntity>
}

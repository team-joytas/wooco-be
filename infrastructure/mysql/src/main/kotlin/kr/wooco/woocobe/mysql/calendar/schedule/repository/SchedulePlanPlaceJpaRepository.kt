package kr.wooco.woocobe.mysql.calendar.schedule.repository

import kr.wooco.woocobe.mysql.calendar.schedule.entity.SchedulePlanPlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SchedulePlanPlaceJpaRepository : JpaRepository<SchedulePlanPlaceJpaEntity, Long> {

    fun findAllByPlanId(planId: Long): List<SchedulePlanPlaceJpaEntity>

    fun findAllByPlanIdIn(planIds: List<Long>): List<SchedulePlanPlaceJpaEntity>

    fun deleteByPlanId(planId: Long)
}

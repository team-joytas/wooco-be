package kr.wooco.woocobe.mysql.schedule.repository

import kr.wooco.woocobe.mysql.schedule.entity.SchedulePlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface SchedulePlanJpaRepository : JpaRepository<SchedulePlanJpaEntity, Long> {

    fun findByIdAndStatus(id: Long, status: String): SchedulePlanJpaEntity?

    fun findAllByGroupIdInAndVisitDateAndStatus(
        groupIds: List<Long>,
        visitDate: LocalDate,
        status: String,
    ): List<SchedulePlanJpaEntity>

    fun findAllByGroupIdInAndVisitDateBetweenAndStatus(
        groupIds: List<Long>,
        startDate: LocalDate,
        endDate: LocalDate,
        status: String,
    ): List<SchedulePlanJpaEntity>
}

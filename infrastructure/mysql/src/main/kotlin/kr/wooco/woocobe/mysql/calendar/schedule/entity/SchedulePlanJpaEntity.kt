package kr.wooco.woocobe.mysql.calendar.schedule.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid
import java.time.LocalDate

@Entity
@Table(name = "schedule_plans")
class SchedulePlanJpaEntity(
    @Column(name = "schedule_plan_status")
    val status: String,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Column(name = "title")
    val title: String,
    @Column(name = "group_id")
    val groupId: Long,
    @Id @Tsid
    @Column(name = "schedule_plan_id")
    override val id: Long = 0L,
) : BaseEntity()

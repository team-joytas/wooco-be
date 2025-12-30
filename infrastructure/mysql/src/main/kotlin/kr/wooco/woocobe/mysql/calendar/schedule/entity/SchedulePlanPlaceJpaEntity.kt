package kr.wooco.woocobe.mysql.calendar.schedule.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "schedule_plan_places")
class SchedulePlanPlaceJpaEntity(
    @Column(name = "schedule_plan_place_order")
    val order: Int,
    @Column(name = "schedule_plan_id")
    val planId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "schedule_plan_place_id")
    override val id: Long,
) : BaseEntity(){
    companion object {
        fun listOf(
            plan: Plan,
            planJpaEntity: SchedulePlanJpaEntity,
        ): List<SchedulePlanPlaceJpaEntity> =
            plan.places.map { planPlace ->
                SchedulePlanPlaceJpaEntity(
                    id = planPlace.id,
                    order = planPlace.order,
                    planId = planJpaEntity.id,
                    placeId = planPlace.placeId,
                )
            }
    }
}

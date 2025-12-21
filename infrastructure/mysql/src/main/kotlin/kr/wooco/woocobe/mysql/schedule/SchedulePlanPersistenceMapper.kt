package kr.wooco.woocobe.mysql.schedule

import kr.wooco.woocobe.core.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.schedule.domain.entity.Plan
import kr.wooco.woocobe.core.schedule.domain.entity.PlanPlace
import kr.wooco.woocobe.mysql.schedule.entity.SchedulePlanJpaEntity
import kr.wooco.woocobe.mysql.schedule.entity.SchedulePlanPlaceJpaEntity

internal object SchedulePlanPersistenceMapper {
    fun toDomainEntity(
        planJpaEntity: SchedulePlanJpaEntity,
        planPlaceJpaEntities: List<SchedulePlanPlaceJpaEntity>,
    ): Plan =
        Plan(
            id = planJpaEntity.id,
            groupId = planJpaEntity.groupId,
            title = Plan.Title(planJpaEntity.title),
            visitDate = Plan.VisitDate(planJpaEntity.visitDate),
            places = planPlaceJpaEntities.map { planPlaceJpaEntity ->
                PlanPlace(
                    id = planPlaceJpaEntity.id,
                    placeId = planPlaceJpaEntity.placeId,
                    order = planPlaceJpaEntity.order,
                )
            },
            status = Plan.Status.valueOf(planJpaEntity.status),
        )

    fun toJpaEntity(plan: Plan): SchedulePlanJpaEntity =
        SchedulePlanJpaEntity(
            id = plan.id,
            groupId = plan.groupId,
            title = plan.title.value,
            visitDate = plan.visitDate.value,
            status = plan.status.name,
        )

    fun toReadModel(
        planJpaEntity: SchedulePlanJpaEntity,
        planPlaceJpaEntities: List<SchedulePlanPlaceJpaEntity>,
    ): PlanView =
        PlanView(
            id = planJpaEntity.id,
            groupId = planJpaEntity.groupId,
            title = planJpaEntity.title,
            visitDate = planJpaEntity.visitDate,
            places = planPlaceJpaEntities.map { planPlace ->
                PlanView.PlanPlaceView(
                    order = planPlace.order,
                    placeId = planPlace.placeId,
                )
            }
        )

    fun update(planJpaEntity: SchedulePlanJpaEntity, plan: Plan): SchedulePlanJpaEntity =
        SchedulePlanJpaEntity(
            id = planJpaEntity.id,
            groupId = plan.groupId,
            title = plan.title.value,
            visitDate = plan.visitDate.value,
            status = plan.status.name,
        )
}

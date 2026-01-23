package kr.wooco.woocobe.mysql.calendar.schedule

import kr.wooco.woocobe.core.calendar.schedule.domain.entity.PlanPlace
import kr.wooco.woocobe.mysql.calendar.schedule.entity.SchedulePlanPlaceJpaEntity

internal object SchedulePlanPlacePersistenceMapper {
    fun toJpaEntities(planPlaces: List<PlanPlace>, planId: Long): List<SchedulePlanPlaceJpaEntity> =
        planPlaces.map { planPlace ->
            SchedulePlanPlaceJpaEntity(
                id = planPlace.id,
                order = planPlace.order,
                planId = planId,
                placeId = planPlace.placeId,
            )
        }
}

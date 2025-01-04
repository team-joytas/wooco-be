package kr.wooco.woocobe.plan.infrastructure.storage

import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanCategory
import kr.wooco.woocobe.plan.domain.model.PlanPlace
import kr.wooco.woocobe.plan.domain.model.PlanRegion
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanCategoryJpaEntity
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanJpaEntity
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanPlaceJpaEntity
import org.springframework.stereotype.Component

@Component
class PlanStorageMapper {
    fun toDomain(
        planJpaEntity: PlanJpaEntity,
        planPlaceJpaEntities: List<PlanPlaceJpaEntity>,
        planCategoryJpaEntities: List<PlanCategoryJpaEntity>,
    ): Plan =
        Plan(
            id = planJpaEntity.id!!,
            userId = planJpaEntity.userId,
            title = planJpaEntity.title,
            description = planJpaEntity.description,
            region = PlanRegion(
                primaryRegion = planJpaEntity.primaryRegion,
                secondaryRegion = planJpaEntity.secondaryRegion,
            ),
            visitDate = planJpaEntity.visitDate,
            places = planPlaceJpaEntities.map { PlanPlace(order = it.order, placeId = it.planId) },
            categories = planCategoryJpaEntities.map { PlanCategory.from(it.name.uppercase()) },
        )

    fun toEntity(plan: Plan): PlanJpaEntity =
        PlanJpaEntity(
            id = plan.id,
            userId = plan.userId,
            title = plan.title,
            description = plan.description,
            primaryRegion = plan.region.primaryRegion,
            secondaryRegion = plan.region.secondaryRegion,
            visitDate = plan.visitDate,
        )
}

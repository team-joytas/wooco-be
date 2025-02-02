package kr.wooco.woocobe.plan.adapter.out.persistence

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanCategoryJpaEntity
import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanJpaEntity
import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanPlaceJpaEntity
import kr.wooco.woocobe.plan.domain.entity.Plan
import kr.wooco.woocobe.plan.domain.entity.PlanCategory
import kr.wooco.woocobe.plan.domain.entity.PlanPlace
import kr.wooco.woocobe.plan.domain.entity.PlanRegion
import org.springframework.stereotype.Component

@Component
class PlanPersistenceMapper {
    fun toDomain(
        planJpaEntity: PlanJpaEntity,
        planPlaceJpaEntities: List<PlanPlaceJpaEntity>,
        planCategoryJpaEntities: List<PlanCategoryJpaEntity>,
    ): Plan =
        Plan(
            id = planJpaEntity.id,
            userId = planJpaEntity.userId,
            title = planJpaEntity.title,
            contents = planJpaEntity.contents,
            region = PlanRegion(
                primaryRegion = planJpaEntity.primaryRegion,
                secondaryRegion = planJpaEntity.secondaryRegion,
            ),
            visitDate = planJpaEntity.visitDate,
            places = planPlaceJpaEntities.map { PlanPlace(order = it.order, placeId = it.placeId) },
            categories = planCategoryJpaEntities.map { PlanCategory.from(it.name.uppercase()) },
        )

    fun toEntity(plan: Plan): PlanJpaEntity =
        PlanJpaEntity(
            id = plan.id,
            userId = plan.userId,
            title = plan.title,
            contents = plan.contents,
            primaryRegion = plan.region.primaryRegion,
            secondaryRegion = plan.region.secondaryRegion,
            visitDate = plan.visitDate,
        )
}

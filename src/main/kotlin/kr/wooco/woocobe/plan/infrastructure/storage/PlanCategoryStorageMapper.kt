package kr.wooco.woocobe.plan.infrastructure.storage

import kr.wooco.woocobe.plan.domain.model.PlanCategory
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanCategoryJpaEntity
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanJpaEntity
import org.springframework.stereotype.Component

@Component
class PlanCategoryStorageMapper {
    fun toEntity(
        planJpaEntity: PlanJpaEntity,
        planCategory: PlanCategory,
    ): PlanCategoryJpaEntity =
        PlanCategoryJpaEntity(
            planId = planJpaEntity.id,
            name = planCategory.name,
        )
}

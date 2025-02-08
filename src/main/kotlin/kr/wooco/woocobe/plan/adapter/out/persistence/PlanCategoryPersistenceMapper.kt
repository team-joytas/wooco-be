package kr.wooco.woocobe.plan.adapter.out.persistence

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanCategoryJpaEntity
import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanJpaEntity
import kr.wooco.woocobe.plan.domain.entity.PlanCategory
import org.springframework.stereotype.Component

@Component
class PlanCategoryPersistenceMapper {
    fun toEntity(
        planJpaEntity: PlanJpaEntity,
        planCategory: PlanCategory,
    ): PlanCategoryJpaEntity =
        PlanCategoryJpaEntity(
            planId = planJpaEntity.id,
            name = planCategory.name,
        )
}

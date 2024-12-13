package kr.wooco.woocobe.plan.infrastructure.gateway

import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.infrastructure.storage.PlanEntity
import kr.wooco.woocobe.plan.infrastructure.storage.PlanJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaPlanStorageGateway(
    private val planJpaRepository: PlanJpaRepository,
) : PlanStorageGateway {
    override fun save(plan: Plan): Plan = planJpaRepository.save(PlanEntity.from(plan)).toDomain()
}

package kr.wooco.woocobe.plan.infrastructure.gateway

import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.infrastructure.storage.PlanEntity
import kr.wooco.woocobe.plan.infrastructure.storage.PlanJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class JpaPlanStorageGateway(
    private val planJpaRepository: PlanJpaRepository,
) : PlanStorageGateway {
    override fun save(plan: Plan): Plan = planJpaRepository.save(PlanEntity.from(plan)).toDomain()

    override fun getById(id: Long): Plan? = planJpaRepository.findByIdOrNull(id)?.toDomain()

    override fun getAllByUserId(userId: Long): List<Plan> =
        planJpaRepository
            .findAllByUserId(userId)
            .map { it.toDomain() }

    override fun deleteById(id: Long) = planJpaRepository.deleteById(id)
}

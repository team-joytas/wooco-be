package kr.wooco.woocobe.plan.infrastructure.gateway

import kr.wooco.woocobe.plan.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.infrastructure.storage.PlanCategoryStorageMapper
import kr.wooco.woocobe.plan.infrastructure.storage.PlanPlaceStorageMapper
import kr.wooco.woocobe.plan.infrastructure.storage.PlanStorageMapper
import kr.wooco.woocobe.plan.infrastructure.storage.repository.PlanCategoryJpaRepository
import kr.wooco.woocobe.plan.infrastructure.storage.repository.PlanJpaRepository
import kr.wooco.woocobe.plan.infrastructure.storage.repository.PlanPlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class PlanStorageGatewayImpl(
    private val planJpaRepository: PlanJpaRepository,
    private val planPlaceJpaRepository: PlanPlaceJpaRepository,
    private val planCategoryJpaRepository: PlanCategoryJpaRepository,
    private val planStorageMapper: PlanStorageMapper,
    private val planPlaceStorageMapper: PlanPlaceStorageMapper,
    private val planCategoryStorageMapper: PlanCategoryStorageMapper,
) : PlanStorageGateway {
    override fun save(plan: Plan): Plan {
        val planEntity = planJpaRepository.save(planStorageMapper.toEntity(plan))

        planPlaceJpaRepository.deleteAllByPlanId(planEntity.id)
        val planPlaceEntities = plan.places.map { planPlaceStorageMapper.toEntity(planEntity, it) }
        planPlaceJpaRepository.saveAll(planPlaceEntities)

        planCategoryJpaRepository.deleteAllByPlanId(planEntity.id)
        val planCategoryEntities = plan.categories.map { planCategoryStorageMapper.toEntity(planEntity, it) }
        planCategoryJpaRepository.saveAll(planCategoryEntities)

        return planStorageMapper.toDomain(planEntity, planPlaceEntities, planCategoryEntities)
    }

    override fun getByPlanId(planId: Long): Plan {
        val planEntity = planJpaRepository.findByIdOrNull(planId)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        val planCategoryEntities = planCategoryJpaRepository.findAllByPlanId(planId)

        return planStorageMapper.toDomain(planEntity, planPlaceEntities, planCategoryEntities)
    }

    override fun getAllByUserId(userId: Long): List<Plan> {
        val planEntities = planJpaRepository.findAllByUserId(userId)
        val planIds = planEntities.map { it.id }
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanIdIn(planIds)
        val planCategoryEntities = planCategoryJpaRepository.findAllByPlanIdIn(planIds)

        return planEntities.map { planJpaEntity ->
            planStorageMapper.toDomain(
                planJpaEntity = planJpaEntity,
                planPlaceJpaEntities = planPlaceEntities.filter { it.planId == planJpaEntity.id },
                planCategoryJpaEntities = planCategoryEntities.filter { it.planId == planJpaEntity.id },
            )
        }
    }

    override fun deleteByPlanId(planId: Long) {
        planJpaRepository.deleteById(planId)
        planPlaceJpaRepository.deleteAllByPlanId(planId)
        planCategoryJpaRepository.deleteAllByPlanId(planId)
    }
}

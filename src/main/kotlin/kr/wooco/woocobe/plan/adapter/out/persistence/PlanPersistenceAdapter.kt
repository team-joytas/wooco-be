package kr.wooco.woocobe.plan.adapter.out.persistence

import kr.wooco.woocobe.plan.adapter.out.persistence.repository.PlanCategoryJpaRepository
import kr.wooco.woocobe.plan.adapter.out.persistence.repository.PlanJpaRepository
import kr.wooco.woocobe.plan.adapter.out.persistence.repository.PlanPlaceJpaRepository
import kr.wooco.woocobe.plan.application.port.out.DeletePlanPersistencePort
import kr.wooco.woocobe.plan.application.port.out.LoadPlanPersistencePort
import kr.wooco.woocobe.plan.application.port.out.SavePlanPersistencePort
import kr.wooco.woocobe.plan.domain.entity.Plan
import kr.wooco.woocobe.plan.domain.exception.NotExistsPlanException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class PlanPersistenceAdapter(
    private val planJpaRepository: PlanJpaRepository,
    private val planPlaceJpaRepository: PlanPlaceJpaRepository,
    private val planCategoryJpaRepository: PlanCategoryJpaRepository,
    private val planPersistenceMapper: PlanPersistenceMapper,
    private val planPlacePersistenceMapper: PlanPlacePersistenceMapper,
    private val planCategoryPersistenceMapper: PlanCategoryPersistenceMapper,
) : LoadPlanPersistencePort,
    DeletePlanPersistencePort,
    SavePlanPersistencePort {
    override fun getByPlanId(planId: Long): Plan {
        val planJpaEntity = planJpaRepository.findByIdOrNull(planId)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        val planCategoryEntities = planCategoryJpaRepository.findAllByPlanId(planId)
        return planPersistenceMapper.toDomain(planJpaEntity, planPlaceEntities, planCategoryEntities)
    }

    override fun getAllByUserId(userId: Long): List<Plan> {
        val planEntities = planJpaRepository.findAllByUserId(userId)
        val planIds = planEntities.map { it.id }
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanIdIn(planIds)
        val planCategoryEntities = planCategoryJpaRepository.findAllByPlanIdIn(planIds)

        return planEntities.map { planJpaEntity ->
            planPersistenceMapper.toDomain(
                planJpaEntity = planJpaEntity,
                planPlaceJpaEntities = planPlaceEntities.filter { it.planId == planJpaEntity.id },
                planCategoryJpaEntities = planCategoryEntities.filter { it.planId == planJpaEntity.id },
            )
        }
    }

    override fun savePlan(plan: Plan): Plan {
        val planEntity = planJpaRepository.save(planPersistenceMapper.toEntity(plan))

        planPlaceJpaRepository.deleteAllByPlanId(planEntity.id)
        val planPlaceEntities = plan.places.map { planPlacePersistenceMapper.toEntity(planEntity, it) }
        planPlaceJpaRepository.saveAll(planPlaceEntities)

        planCategoryJpaRepository.deleteAllByPlanId(planEntity.id)
        val planCategoryEntities = plan.categories.map { planCategoryPersistenceMapper.toEntity(planEntity, it) }
        planCategoryJpaRepository.saveAll(planCategoryEntities)

        return planPersistenceMapper.toDomain(planEntity, planPlaceEntities, planCategoryEntities)
    }

    override fun deleteByPlanId(planId: Long) {
        planJpaRepository.deleteById(planId)
        planPlaceJpaRepository.deleteAllByPlanId(planId)
        planCategoryJpaRepository.deleteAllByPlanId(planId)
    }
}

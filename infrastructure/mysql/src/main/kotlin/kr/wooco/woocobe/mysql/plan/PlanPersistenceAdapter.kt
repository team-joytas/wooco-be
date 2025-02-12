package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.application.port.out.DeletePlanPersistencePort
import kr.wooco.woocobe.core.plan.application.port.out.LoadPlanPersistencePort
import kr.wooco.woocobe.core.plan.application.port.out.SavePlanPersistencePort
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.mysql.plan.repository.PlanJpaRepository
import kr.wooco.woocobe.mysql.plan.repository.PlanPlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class PlanPersistenceAdapter(
    private val planJpaRepository: PlanJpaRepository,
    private val planPlaceJpaRepository: PlanPlaceJpaRepository,
    private val planPersistenceMapper: PlanPersistenceMapper,
    private val planPlacePersistenceMapper: PlanPlacePersistenceMapper,
) : LoadPlanPersistencePort,
    DeletePlanPersistencePort,
    SavePlanPersistencePort {
    override fun getByPlanId(planId: Long): Plan {
        val planJpaEntity = planJpaRepository.findByIdOrNull(planId)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        return planPersistenceMapper.toDomain(planJpaEntity, planPlaceEntities)
    }

    override fun getAllByUserId(userId: Long): List<Plan> {
        val planEntities = planJpaRepository.findAllByUserId(userId)
        val planIds = planEntities.map { it.id }
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanIdIn(planIds)

        return planEntities.map { planJpaEntity ->
            planPersistenceMapper.toDomain(
                planJpaEntity = planJpaEntity,
                planPlaceJpaEntities = planPlaceEntities.filter { it.planId == planJpaEntity.id },
            )
        }
    }

    override fun savePlan(plan: Plan): Plan {
        val planEntity = planJpaRepository.save(planPersistenceMapper.toEntity(plan))

        planPlaceJpaRepository.deleteAllByPlanId(planEntity.id)
        val planPlaceEntities = plan.places.map { planPlacePersistenceMapper.toEntity(planEntity, it) }
        planPlaceJpaRepository.saveAll(planPlaceEntities)

        return planPersistenceMapper.toDomain(planEntity, planPlaceEntities)
    }

    override fun deleteByPlanId(planId: Long) {
        planJpaRepository.deleteById(planId)
        planPlaceJpaRepository.deleteAllByPlanId(planId)
    }
}

package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.application.port.out.PlanCommandPort
import kr.wooco.woocobe.core.plan.application.port.out.PlanQueryPort
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity
import kr.wooco.woocobe.mysql.plan.repository.PlanJpaRepository
import kr.wooco.woocobe.mysql.plan.repository.PlanPlaceJpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
internal class PlanPersistenceAdapter(
    private val planJpaRepository: PlanJpaRepository,
    private val planPlaceJpaRepository: PlanPlaceJpaRepository,
) : PlanQueryPort,
    PlanCommandPort {
    override fun getByPlanId(planId: Long): Plan {
        val planJpaEntity = planJpaRepository.findActiveById(planId)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        return PlanPersistenceMapper.toDomainEntity(planJpaEntity, planPlaceEntities)
    }

    override fun getAllByUserId(userId: Long): List<Plan> {
        val planEntities = planJpaRepository.findAllActiveByUserId(userId)
        val planPlaceEntities = getPlanPlaceEntities(planEntities)
        return getPlansWithPlanPlaces(planEntities, planPlaceEntities)
    }

    override fun getAllByCreatedAtBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Plan> {
        val planEntities = planJpaRepository.findAllActiveByCreatedAtBetween(
            startDate = startDate,
            endDate = endDate,
        )
        val planPlaceEntities = getPlanPlaceEntities(planEntities)
        return getPlansWithPlanPlaces(planEntities, planPlaceEntities)
    }

    override fun savePlan(plan: Plan): Plan {
        val planEntity = planJpaRepository.save(PlanPersistenceMapper.toJpaEntity(plan))

        planPlaceJpaRepository.deleteAllByPlanId(planEntity.id)
        val planPlaceEntities = plan.places.map { PlanPersistenceMapper.toPlanPlaceJpaEntity(planEntity.id, it) }
        planPlaceJpaRepository.saveAll(planPlaceEntities)

        return PlanPersistenceMapper.toDomainEntity(planEntity, planPlaceEntities)
    }

    override fun deleteAllPlanPlaceByPlanId(planId: Long) {
        planPlaceJpaRepository.deleteAllByPlanId(planId)
    }

    private fun getPlanPlaceEntities(planEntities: List<PlanJpaEntity>): List<PlanPlaceJpaEntity> {
        val planIds = planEntities.map { it.id }
        return planPlaceJpaRepository.findAllByPlanIdIn(planIds)
    }

    private fun getPlansWithPlanPlaces(
        planEntities: List<PlanJpaEntity>,
        planPlaceEntities: List<PlanPlaceJpaEntity>,
    ): List<Plan> =
        planEntities.map { planJpaEntity ->
            PlanPersistenceMapper.toDomainEntity(
                planJpaEntity = planJpaEntity,
                planPlaceJpaEntities = planPlaceEntities.filter { it.planId == planJpaEntity.id },
            )
        }
}

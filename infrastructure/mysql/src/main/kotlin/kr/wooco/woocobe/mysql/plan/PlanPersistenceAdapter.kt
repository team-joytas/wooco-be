package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.application.port.out.PlanCommandPort
import kr.wooco.woocobe.core.plan.application.port.out.PlanQueryPort
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.core.plan.domain.vo.PlanStatus
import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity
import kr.wooco.woocobe.mysql.plan.repository.PlanJpaRepository
import kr.wooco.woocobe.mysql.plan.repository.PlanPlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
internal class PlanPersistenceAdapter(
    private val planJpaRepository: PlanJpaRepository,
    private val planPlaceJpaRepository: PlanPlaceJpaRepository,
) : PlanQueryPort,
    PlanCommandPort {
    override fun getByPlanId(planId: Long): Plan {
        val planJpaEntity = planJpaRepository.findByIdOrNull(planId)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        return PlanPersistenceMapper.toDomainEntity(planJpaEntity, planPlaceEntities)
    }

    override fun getByPlanIdWithActive(planId: Long): Plan {
        val planJpaEntity = planJpaRepository.findByIdAndStatus(planId, PlanStatus.ACTIVE.name)
            ?: throw NotExistsPlanException
        val planPlaceEntities = planPlaceJpaRepository.findAllByPlanId(planId)
        return PlanPersistenceMapper.toDomainEntity(planJpaEntity, planPlaceEntities)
    }

    override fun getAllByUserIdWithActive(userId: Long): List<Plan> {
        val planEntities = planJpaRepository.findAllByUserIdAndStatus(userId, PlanStatus.ACTIVE.name)
        val planPlaceEntities = getPlanPlaceEntities(planEntities)
        return getPlansWithPlanPlaces(planEntities, planPlaceEntities)
    }

    override fun getAllByCreatedAtBetweenWithActive(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Plan> {
        val planEntities = planJpaRepository.findAllByCreatedAtBetweenAndStatus(
            startDate = startDate,
            endDate = endDate,
            status = PlanStatus.ACTIVE.name,
        )
        val planPlaceEntities = getPlanPlaceEntities(planEntities)
        return getPlansWithPlanPlaces(planEntities, planPlaceEntities)
    }

    // TODO: 애그리거트 루트 도입 후 리팩토링 예정
    override fun savePlan(plan: Plan): Plan {
        val planEntity = planJpaRepository.save(PlanPersistenceMapper.toJpaEntity(plan))

        planPlaceJpaRepository.deleteAllByPlanId(planEntity.id)
        val planPlaceEntities = plan.places.map { PlanPersistenceMapper.toPlanPlaceJpaEntity(planEntity.id, it) }
        planPlaceJpaRepository.saveAll(planPlaceEntities)

        return PlanPersistenceMapper.toDomainEntity(planEntity, planPlaceEntities)
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

package kr.wooco.woocobe.mysql.calendar.schedule

import jakarta.transaction.Transactional
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanCommandPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanQueryPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan
import kr.wooco.woocobe.core.calendar.schedule.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.mysql.calendar.schedule.entity.SchedulePlanPlaceJpaEntity
import kr.wooco.woocobe.mysql.calendar.schedule.repository.SchedulePlanJpaRepository
import kr.wooco.woocobe.mysql.calendar.schedule.repository.SchedulePlanPlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
internal class ScheduleScheduleSchedulePlanPersistenceAdapter(
    private val schedulePlanJpaRepository: SchedulePlanJpaRepository,
    private val schedulePlanPlaceJpaRepository: SchedulePlanPlaceJpaRepository,
) : SchedulePlanCommandPort,
    SchedulePlanQueryPort {
    @Transactional
    override fun save(plan: Plan): Long {
        return if (plan.isNew()) {
            saveNew(plan)
        } else {
            saveExisting(plan)
        }
    }

    override fun getById(planId: Long): Plan {
        val planJpaEntity = schedulePlanJpaRepository.findByIdOrNull(planId)
            ?: throw NotExistsPlanException
        val planPlaceJpaEntities = schedulePlanPlaceJpaRepository.findAllByPlanId(planId)
        return SchedulePlanPersistenceMapper.toDomainEntity(planJpaEntity, planPlaceJpaEntities)
    }

    override fun getViewById(planId: Long): PlanView {
        val planJpaEntity = schedulePlanJpaRepository.findByIdAndStatus(id = planId, status = Plan.Status.ACTIVE.name)
            ?: throw NotExistsPlanException
        val planPlaceJpaEntities = schedulePlanPlaceJpaRepository.findAllByPlanId(planId)
        return SchedulePlanPersistenceMapper.toReadModel(planJpaEntity, planPlaceJpaEntities)
    }

    // 일간 조회
    override fun getViewAllByGroupIdInAndVisitDate(
        groupIds: List<Long>,
        visitDate: LocalDate
    ): List<PlanView> {
        val plans = schedulePlanJpaRepository.findAllByGroupIdInAndVisitDateAndStatus(
            groupIds = groupIds,
            visitDate = visitDate,
            status = Plan.Status.ACTIVE.name,
        )

        val planPlaceJpaEntities = schedulePlanPlaceJpaRepository.findAllByPlanIdIn(plans.map { it.id })
        return plans.map {
            SchedulePlanPersistenceMapper.toReadModel(it, planPlaceJpaEntities)
        }
    }

    // 날짜 범위 기반 조회
    override fun getViewAllByGroupIdInAndVisitDateBetween(
        groupIds: List<Long>,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<PlanView> {
        val plans = schedulePlanJpaRepository.findAllByGroupIdInAndVisitDateBetweenAndStatus(
            groupIds = groupIds,
            startDate = startDate,
            endDate = endDate,
            status = Plan.Status.ACTIVE.name,
        )

        val planPlaceJpaEntities = schedulePlanPlaceJpaRepository.findAllByPlanIdIn(plans.map { it.id })
        val planPlacesByPlanId = planPlaceJpaEntities.groupBy { it.planId }
        return plans.map { plan ->
            val placesOfPlan = planPlacesByPlanId[plan.id].orEmpty()
            SchedulePlanPersistenceMapper.toReadModel(plan, placesOfPlan)
        }
    }

    private fun saveNew(plan: Plan): Long {
        val planJpaEntity = schedulePlanJpaRepository.save(SchedulePlanPersistenceMapper.toJpaEntity(plan))
        val planPlaceJpaEntities = SchedulePlanPlaceJpaEntity.Companion.listOf(plan = plan, planJpaEntity = planJpaEntity)
        schedulePlanPlaceJpaRepository.saveAll(planPlaceJpaEntities)
        return planJpaEntity.id
    }

    private fun saveExisting(plan: Plan): Long {
        val planJpaEntity = schedulePlanJpaRepository.findByIdOrNull(plan.id)
            ?: throw NotExistsPlanException
        val updatedPlanJpaEntity = schedulePlanJpaRepository.save(
            SchedulePlanPersistenceMapper.update(planJpaEntity = planJpaEntity, plan = plan)
        )
        val newPlanPlaceJpaEntities = SchedulePlanPlacePersistenceMapper.toJpaEntities(
            planPlaces = plan.places,
            planId = updatedPlanJpaEntity.id
        )
        syncPlanPlaces(planId = updatedPlanJpaEntity.id, planPlaceJpaEntities = newPlanPlaceJpaEntities)
        return updatedPlanJpaEntity.id
    }

    private fun syncPlanPlaces(planId: Long, planPlaceJpaEntities: List<SchedulePlanPlaceJpaEntity>) {
        schedulePlanPlaceJpaRepository.deleteByPlanId(planId)
        schedulePlanPlaceJpaRepository.saveAll(planPlaceJpaEntities)
    }
}

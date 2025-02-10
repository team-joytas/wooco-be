package kr.wooco.woocobe.plan.application.service

import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.plan.application.port.`in`.ReadAllPlanUseCase
import kr.wooco.woocobe.plan.application.port.`in`.ReadPlanUseCase
import kr.wooco.woocobe.plan.application.port.`in`.results.PlanResult
import kr.wooco.woocobe.plan.application.port.out.LoadPlanPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlanQueryService(
    private val loadPlanPersistencePort: LoadPlanPersistencePort,
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
) : ReadPlanUseCase,
    ReadAllPlanUseCase {
    @Transactional(readOnly = true)
    override fun readPlan(query: ReadPlanUseCase.Query): PlanResult {
        val plan = loadPlanPersistencePort.getByPlanId(query.planId)
        val placeIds = plan.places.map { it.placeId }
        val places = loadPlacePersistencePort.getAllByPlaceIds(placeIds)
        return PlanResult.of(plan, places)
    }

    @Transactional(readOnly = true)
    override fun readAllPlan(query: ReadAllPlanUseCase.Query): List<PlanResult> {
        val plans = loadPlanPersistencePort.getAllByUserId(query.userId)
        val placeIds = plans.flatMap { it.places }.map { it.placeId }
        val places = loadPlacePersistencePort.getAllByPlaceIds(placeIds)
        return PlanResult.listOf(plans, places)
    }
}

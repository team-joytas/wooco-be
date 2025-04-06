package kr.wooco.woocobe.core.plan.application.service

import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.plan.application.port.`in`.ReadAllPlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.ReadPlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.results.PlanResult
import kr.wooco.woocobe.core.plan.application.port.out.PlanQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlanQueryService(
    private val planQueryPort: PlanQueryPort,
    private val placeQueryPort: PlaceQueryPort,
) : ReadPlanUseCase,
    ReadAllPlanUseCase {
    @Transactional(readOnly = true)
    override fun readPlan(query: ReadPlanUseCase.Query): PlanResult {
        val plan = planQueryPort.getActiveByPlanId(query.planId)
        val placeIds = plan.places.map { it.placeId }
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        return PlanResult.of(plan, places)
    }

    @Transactional(readOnly = true)
    override fun readAllPlan(query: ReadAllPlanUseCase.Query): List<PlanResult> {
        val plans = planQueryPort.getAllActiveByUserId(query.userId)
        val placeIds = plans.flatMap { it.places }.map { it.placeId }
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        return PlanResult.listOf(plans, places)
    }
}

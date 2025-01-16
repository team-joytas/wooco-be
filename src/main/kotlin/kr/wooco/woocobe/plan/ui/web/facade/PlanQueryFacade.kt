package kr.wooco.woocobe.plan.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceUseCase
import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanInput
import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.GetPlanInput
import kr.wooco.woocobe.plan.domain.usecase.GetPlanUseCase
import kr.wooco.woocobe.plan.ui.web.controller.response.PlanDetailResponse
import org.springframework.stereotype.Service

@Service
class PlanQueryFacade(
    private val getPlanUseCase: GetPlanUseCase,
    private val getAllPlanUseCase: GetAllPlanUseCase,
    private val getAllPlaceUseCase: GetAllPlaceUseCase,
) {
    fun getPlanDetail(
        userId: Long,
        planId: Long,
    ): PlanDetailResponse {
        val getPlanResult = getPlanUseCase.execute(GetPlanInput(userId = userId, planId = planId))

        val placeIds = getPlanResult.plan.places.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        return PlanDetailResponse.of(
            plan = getPlanResult.plan,
            places = getAllPlaceResult.places,
        )
    }

    fun getAllPlanDetail(userId: Long): List<PlanDetailResponse> {
        val getAllPlanResult = getAllPlanUseCase.execute(GetAllPlanInput(userId = userId))

        val placeIds = getAllPlanResult.plans.flatMap { it.places }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        return PlanDetailResponse.listOf(
            plans = getAllPlanResult.plans,
            places = getAllPlaceResult.places,
        )
    }
}

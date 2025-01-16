package kr.wooco.woocobe.plan.ui.web.controller

import kr.wooco.woocobe.plan.ui.web.controller.request.CreatePlanRequest
import kr.wooco.woocobe.plan.ui.web.controller.request.UpdatePlanRequest
import kr.wooco.woocobe.plan.ui.web.controller.response.CreatePlanResponse
import kr.wooco.woocobe.plan.ui.web.controller.response.PlanDetailResponse
import kr.wooco.woocobe.plan.ui.web.facade.PlanCommandFacade
import kr.wooco.woocobe.plan.ui.web.facade.PlanQueryFacade
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plans")
class PlanController(
    private val planQueryFacade: PlanQueryFacade,
    private val planCommandFacade: PlanCommandFacade,
) : PlanApi {
    @PostMapping
    override fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlanRequest,
    ): ResponseEntity<CreatePlanResponse> {
        val response = planCommandFacade.createPlan(
            userId = userId,
            request = request,
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping
    override fun getAllPlanDetail(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PlanDetailResponse>> {
        val response = planQueryFacade.getAllPlanDetail(userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{planId}")
    override fun getPlanDetail(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<PlanDetailResponse> {
        val response = planQueryFacade.getPlanDetail(
            userId = userId,
            planId = planId,
        )
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{planId}")
    override fun updatePlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdatePlanRequest,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        planCommandFacade.updatePlan(
            userId = userId,
            planId = planId,
            request = request,
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{planId}")
    override fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        planCommandFacade.deletePlan(
            userId = userId,
            planId = planId,
        )
        return ResponseEntity.ok().build()
    }
}

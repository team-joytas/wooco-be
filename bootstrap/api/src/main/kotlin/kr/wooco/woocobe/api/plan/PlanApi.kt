package kr.wooco.woocobe.api.plan

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.plan.request.CreatePlanRequest
import kr.wooco.woocobe.api.plan.request.UpdatePlanRequest
import kr.wooco.woocobe.api.plan.response.CreatePlanResponse
import kr.wooco.woocobe.api.plan.response.PlanDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "플랜 API")
interface PlanApi {
    @SecurityRequirement(name = "JWT")
    fun getPlanDetail(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<PlanDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun getAllPlanDetail(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PlanDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlanRequest,
    ): ResponseEntity<CreatePlanResponse>

    @SecurityRequirement(name = "JWT")
    fun updatePlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdatePlanRequest,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun sharePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit>
}

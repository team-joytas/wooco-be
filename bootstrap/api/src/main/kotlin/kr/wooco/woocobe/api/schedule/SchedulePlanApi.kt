package kr.wooco.woocobe.api.schedule

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.schedule.request.CreatePlanRequest
import kr.wooco.woocobe.api.schedule.request.UpdatePlanInfoRequest
import kr.wooco.woocobe.api.schedule.response.CreatePlanResponse
import kr.wooco.woocobe.api.schedule.response.PlanDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "플랜 API")
interface SchedulePlanApi {

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "플랜 생성", description = "새로운 플랜을 생성합니다.")
    fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlanRequest,
    ): ResponseEntity<CreatePlanResponse>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "플랜 정보 수정", description = "플랜 정보를 수정합니다.")
    fun updatePlanInfo(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
        @RequestBody request: UpdatePlanInfoRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "플랜 삭제", description = "플랜을 삭제합니다.")
    fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "플랜 상세 조회", description = "특정 플랜 상세를 조회합니다.")
    fun readPlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<PlanDetailResponse>
}

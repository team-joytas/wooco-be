package kr.wooco.woocobe.api.schedule

import kr.wooco.woocobe.api.schedule.request.CreatePlanRequest
import kr.wooco.woocobe.api.schedule.request.UpdatePlanInfoRequest
import kr.wooco.woocobe.api.schedule.response.CreatePlanResponse
import kr.wooco.woocobe.api.schedule.response.PlanDetailResponse
import kr.wooco.woocobe.core.schedule.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.schedule.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.schedule.application.port.`in`.ReadPlanUseCase
import kr.wooco.woocobe.core.schedule.application.port.`in`.UpdatePlanInfoUseCase
import org.springframework.http.HttpStatus
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
@RequestMapping("/api/v2/plans")
class SchedulePlanController(
    private val createPlanUseCase: CreatePlanUseCase,
    private val updatePlanInfoUseCase: UpdatePlanInfoUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val readPlanUseCase: ReadPlanUseCase,
) : SchedulePlanApi {

    @PostMapping
    override fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlanRequest
    ): ResponseEntity<CreatePlanResponse> {
        val command = request.toCommand(userId)
        val result = createPlanUseCase.createPlan(command)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CreatePlanResponse(result))
    }

    @PatchMapping("/{planId}")
    override fun updatePlanInfo(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
        @RequestBody request: UpdatePlanInfoRequest
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId)
        updatePlanInfoUseCase.updatePlanInfo(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{planId}")
    override fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        val command = DeletePlanUseCase.Command(userId = userId, planId = planId)
        deletePlanUseCase.deletePlan(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{planId}")
    override fun readPlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long
    ): ResponseEntity<PlanDetailResponse> {
        val query = ReadPlanUseCase.Query(userId = userId, planId = planId)
        val result = readPlanUseCase.readPlan(query)
        return ResponseEntity.ok().body(PlanDetailResponse.from(result))
    }
}

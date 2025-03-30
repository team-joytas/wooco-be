package kr.wooco.woocobe.api.plan

import kr.wooco.woocobe.api.common.security.LoginRequired
import kr.wooco.woocobe.api.plan.request.CreatePlanRequest
import kr.wooco.woocobe.api.plan.request.UpdatePlanRequest
import kr.wooco.woocobe.api.plan.response.CreatePlanResponse
import kr.wooco.woocobe.api.plan.response.PlanDetailResponse
import kr.wooco.woocobe.core.plan.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.ReadAllPlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.ReadPlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.UpdatePlanUseCase
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
    private val createPlanUseCase: CreatePlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val readPlanUseCase: ReadPlanUseCase,
    private val readAllPlanUseCase: ReadAllPlanUseCase,
) : PlanApi {
    @LoginRequired
    @PostMapping
    override fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlanRequest,
    ): ResponseEntity<CreatePlanResponse> {
        val command = request.toCommand(userId)
        val response = createPlanUseCase.createPlan(command)
        return ResponseEntity.ok(CreatePlanResponse(response))
    }

    @LoginRequired
    @GetMapping
    override fun getAllPlanDetail(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PlanDetailResponse>> {
        val query = ReadAllPlanUseCase.Query(userId)
        val response = readAllPlanUseCase.readAllPlan(query)
        return ResponseEntity.ok(PlanDetailResponse.listOf(response))
    }

    @LoginRequired
    @GetMapping("/{planId}")
    override fun getPlanDetail(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<PlanDetailResponse> {
        val query = ReadPlanUseCase.Query(userId = userId, planId = planId)
        val response = readPlanUseCase.readPlan(query)
        return ResponseEntity.ok(PlanDetailResponse.from(response))
    }

    @LoginRequired
    @PatchMapping("/{planId}")
    override fun updatePlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdatePlanRequest,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId = userId, planId = planId)
        updatePlanUseCase.updatePlan(command)
        return ResponseEntity.ok().build()
    }

    @LoginRequired
    @DeleteMapping("/{planId}")
    override fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        val command = DeletePlanUseCase.Command(userId = userId, planId = planId)
        deletePlanUseCase.deletePlan(command)
        return ResponseEntity.ok().build()
    }
}

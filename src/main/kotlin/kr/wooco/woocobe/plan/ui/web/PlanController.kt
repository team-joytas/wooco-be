package kr.wooco.woocobe.plan.ui.web

import kr.wooco.woocobe.plan.domain.usecase.AddPlanInput
import kr.wooco.woocobe.plan.domain.usecase.AddPlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.DeletePlanInput
import kr.wooco.woocobe.plan.domain.usecase.DeletePlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanInput
import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.GetPlanInput
import kr.wooco.woocobe.plan.domain.usecase.GetPlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.UpdatePlanInput
import kr.wooco.woocobe.plan.domain.usecase.UpdatePlanUseCase
import kr.wooco.woocobe.plan.ui.web.dto.request.AddPlanRequest
import kr.wooco.woocobe.plan.ui.web.dto.request.UpdatePlanRequest
import kr.wooco.woocobe.plan.ui.web.dto.response.GetAllPlanResponse
import kr.wooco.woocobe.plan.ui.web.dto.response.GetPlanResponse
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
    private val addPlanUseCase: AddPlanUseCase,
    private val getAllPlanUseCase: GetAllPlanUseCase,
    private val getPlanUseCase: GetPlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
) {
    @PostMapping
    fun createPlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: AddPlanRequest,
    ): ResponseEntity<Unit> {
        addPlanUseCase.execute(
            AddPlanInput(
                userId = userId,
                primaryRegion = request.primaryRegion,
                secondaryRegion = request.secondaryRegion,
                visitDate = request.visitDate,
            ),
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllPlans(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<GetAllPlanResponse> {
        val response = GetAllPlanResponse.from(
            getAllPlanUseCase.execute(GetAllPlanInput(userId)),
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{planId}")
    fun getPlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<GetPlanResponse> {
        val response = GetPlanResponse.from(
            getPlanUseCase.execute(
                GetPlanInput(
                    userId = userId,
                    planId = planId,
                ),
            ),
        )
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{planId}")
    fun updatePlan(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: UpdatePlanRequest,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        updatePlanUseCase.execute(
            UpdatePlanInput(
                userId = userId,
                planId = planId,
                primaryRegion = request.primaryRegion,
                secondaryRegion = request.secondaryRegion,
                visitDate = request.visitDate,
            ),
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{planId}")
    fun deletePlan(
        @AuthenticationPrincipal userId: Long,
        @PathVariable planId: Long,
    ): ResponseEntity<Unit> {
        deletePlanUseCase.execute(
            DeletePlanInput(
                userId = userId,
                planId = planId,
            ),
        )
        return ResponseEntity.ok().build()
    }
}

package kr.wooco.woocobe.api.region

import kr.wooco.woocobe.api.region.request.CreatePreferenceRegionRequest
import kr.wooco.woocobe.api.region.response.CreatePreferenceRegionResponse
import kr.wooco.woocobe.api.region.response.PreferenceDetailResponse
import kr.wooco.woocobe.core.region.application.command.port.`in`.AddPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.command.port.`in`.DeletePreferenceRegionCommand
import kr.wooco.woocobe.core.region.application.command.port.`in`.DeletePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetAllPreferenceRegionQuery
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetAllPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetPreferenceRegionQuery
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetPreferenceRegionUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/regions")
class RegionController(
    private val getPreferenceRegionUseCase: GetPreferenceRegionUseCase,
    private val getAllPreferenceRegionUseCase: GetAllPreferenceRegionUseCase,
    private val addPreferenceRegionUseCase: AddPreferenceRegionUseCase,
    private val deletePreferenceRegionUseCase: DeletePreferenceRegionUseCase,
) : RegionApi {
    @PostMapping("/preferences")
    override fun addPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePreferenceRegionRequest,
    ): ResponseEntity<CreatePreferenceRegionResponse> {
        val command = request.toCommand(userId)
        val results = addPreferenceRegionUseCase.addPreferenceRegion(command)
        val response = CreatePreferenceRegionResponse.from(results)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/preferences/check")
    override fun checkPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestParam(name = "primary_region") primaryRegion: String,
        @RequestParam(name = "secondary_region") secondaryRegion: String,
    ): ResponseEntity<PreferenceDetailResponse> {
        val query = GetPreferenceRegionQuery(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )
        val results = getPreferenceRegionUseCase.getPreferenceRegion(query)
        return ResponseEntity.ok(PreferenceDetailResponse.from(results))
    }

    @GetMapping("/preferences")
    override fun getAllMyPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PreferenceDetailResponse>> {
        val query = GetAllPreferenceRegionQuery(userId = userId)
        val results = getAllPreferenceRegionUseCase.getAllPreferenceRegion(query)
        val response = PreferenceDetailResponse.listFrom(results)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/preferences/{preferenceRegionId}")
    override fun deletePreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @PathVariable preferenceRegionId: Long,
    ): ResponseEntity<Unit> {
        val command = DeletePreferenceRegionCommand(userId = userId, preferenceRegionId = preferenceRegionId)
        deletePreferenceRegionUseCase.deletePreferenceRegion(command)
        return ResponseEntity.ok().build()
    }
}

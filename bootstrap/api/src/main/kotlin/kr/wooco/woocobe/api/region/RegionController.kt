package kr.wooco.woocobe.api.region

import kr.wooco.woocobe.api.region.request.CreatePreferenceRegionRequest
import kr.wooco.woocobe.api.region.response.CreatePreferenceRegionResponse
import kr.wooco.woocobe.api.region.response.PreferenceRegionResponse
import kr.wooco.woocobe.core.region.application.port.`in`.CreatePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.DeletePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.ReadAllPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.ReadPreferenceRegionUseCase
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
    private val readPreferenceRegionUseCase: ReadPreferenceRegionUseCase,
    private val createPreferenceRegionUseCase: CreatePreferenceRegionUseCase,
    private val deletePreferenceRegionUseCase: DeletePreferenceRegionUseCase,
    private val readAllPreferenceRegionUseCase: ReadAllPreferenceRegionUseCase,
) : RegionApi {
    @PostMapping("/preferences")
    override fun addPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePreferenceRegionRequest,
    ): ResponseEntity<CreatePreferenceRegionResponse> {
        val command = request.toCommand(userId)
        val results = createPreferenceRegionUseCase.createPreferenceRegion(command)
        val response = CreatePreferenceRegionResponse.from(results)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/preferences/check")
    override fun checkPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestParam(name = "primary_region") primaryRegion: String,
        @RequestParam(name = "secondary_region") secondaryRegion: String,
    ): ResponseEntity<PreferenceRegionResponse> {
        val query = ReadPreferenceRegionUseCase.Query(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )
        val results = readPreferenceRegionUseCase.readPreferenceRegion(query)
        return ResponseEntity.ok(PreferenceRegionResponse.from(results))
    }

    @GetMapping("/preferences")
    override fun getAllMyPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PreferenceRegionResponse>> {
        val query = ReadAllPreferenceRegionUseCase.Query(userId = userId)
        val results = readAllPreferenceRegionUseCase.readAllPreferenceRegion(query)
        val response = PreferenceRegionResponse.listFrom(results)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/preferences/{preferenceRegionId}")
    override fun deletePreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @PathVariable preferenceRegionId: Long,
    ): ResponseEntity<Unit> {
        val command = DeletePreferenceRegionUseCase.Command(
            userId = userId,
            preferenceRegionId = preferenceRegionId,
        )
        deletePreferenceRegionUseCase.deletePreferenceRegion(command)
        return ResponseEntity.ok().build()
    }
}

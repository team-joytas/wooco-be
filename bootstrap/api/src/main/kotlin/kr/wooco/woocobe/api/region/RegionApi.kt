package kr.wooco.woocobe.api.region

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.region.request.CreatePreferenceRegionRequest
import kr.wooco.woocobe.api.region.response.CreatePreferenceRegionResponse
import kr.wooco.woocobe.api.region.response.PreferenceDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "지역 API")
interface RegionApi {
    @SecurityRequirement(name = "JWT")
    fun addPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePreferenceRegionRequest,
    ): ResponseEntity<CreatePreferenceRegionResponse>

    @SecurityRequirement(name = "JWT")
    fun checkPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @RequestParam(name = "primary_region") primaryRegion: String,
        @RequestParam(name = "secondary_region") secondaryRegion: String,
    ): ResponseEntity<PreferenceDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun getAllMyPreferenceRegion(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PreferenceDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun deletePreferenceRegion(
        @AuthenticationPrincipal userId: Long,
        @PathVariable preferenceRegionId: Long,
    ): ResponseEntity<Unit>
}

package kr.wooco.woocobe.place.ui.web.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceDetailResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceOneLineReviewStatsDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "장소 API")
interface PlaceApi {
    fun getPlaceDetail(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceDetailResponse>

    fun getPlaceOneLineReviewStats(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceOneLineReviewStatsDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun createPlace(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlaceRequest,
    ): ResponseEntity<CreatePlaceResponse>
}

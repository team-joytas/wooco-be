package kr.wooco.woocobe.api.place

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.place.request.CreatePlaceRequest
import kr.wooco.woocobe.api.place.response.CreatePlaceResponse
import kr.wooco.woocobe.api.place.response.PlaceDetailResponse
import kr.wooco.woocobe.api.place.response.PlaceWithPlaceReviewsDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "장소 API")
interface PlaceApi {
    fun getPlaceDetail(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun createPlace(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlaceRequest,
    ): ResponseEntity<CreatePlaceResponse>

    fun getPlaceDetailWithPlaceReview(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceWithPlaceReviewsDetailResponse>
}

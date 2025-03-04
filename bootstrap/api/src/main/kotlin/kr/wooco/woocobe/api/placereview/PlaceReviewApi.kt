package kr.wooco.woocobe.api.placereview

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.placereview.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.api.placereview.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.api.placereview.response.CreatePlaceReviewResponse
import kr.wooco.woocobe.api.placereview.response.PlaceReviewWithPlaceDetailsResponse
import kr.wooco.woocobe.api.placereview.response.PlaceReviewWithWriterDetailsResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "장소 리뷰 API")
interface PlaceReviewApi {
    fun getPlaceReviewDetail(
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<PlaceReviewWithWriterDetailsResponse>

    fun getAllPlaceReview(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceReviewWithWriterDetailsResponse>>

    fun getAllUserPlaceReview(
        @PathVariable userId: Long,
    ): ResponseEntity<List<PlaceReviewWithPlaceDetailsResponse>>

    @SecurityRequirement(name = "JWT")
    fun createPlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeId: Long,
        @RequestBody request: CreatePlaceReviewRequest,
    ): ResponseEntity<CreatePlaceReviewResponse>

    @SecurityRequirement(name = "JWT")
    fun updatePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
        @RequestBody request: UpdatePlaceReviewRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun deletePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<Unit>
}

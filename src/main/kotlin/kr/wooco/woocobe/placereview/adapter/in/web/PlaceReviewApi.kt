package kr.wooco.woocobe.placereview.adapter.`in`.web

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.placereview.adapter.`in`.web.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.placereview.adapter.`in`.web.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.placereview.adapter.`in`.web.response.CreatePlaceReviewResponse
import kr.wooco.woocobe.placereview.adapter.`in`.web.response.PlaceReviewDetailsResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "장소 리뷰 API")
interface PlaceReviewApi {
    fun getPlaceReviewDetail(
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<PlaceReviewDetailsResponse>

    fun getAllPlaceReview(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>>

    fun getAllMyPlaceReview(
        @PathVariable userId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>>

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

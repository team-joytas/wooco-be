package kr.wooco.woocobe.place.ui.web.controller

import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceReviewResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceReviewDetailsResponse
import kr.wooco.woocobe.place.ui.web.facade.PlaceReviewCommandFacade
import kr.wooco.woocobe.place.ui.web.facade.PlaceReviewQueryFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/reviews")
class PlaceReviewController(
    private val placeReviewQueryFacade: PlaceReviewQueryFacade,
    private val placeReviewCommandFacade: PlaceReviewCommandFacade,
) {
    @GetMapping("/{placeReviewId}")
    fun getPlaceReviewDetail(
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<PlaceReviewDetailsResponse> {
        val response = placeReviewQueryFacade.getPlaceReviewDetail(placeReviewId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/places/{placeId}")
    fun getAllPlaceReviews(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>> {
        val response = placeReviewQueryFacade.getAllPlaceReviewDetail(placeId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/my")
    fun getAllMyPlaceReviews(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>> {
        val response = placeReviewQueryFacade.getAllMyPlaceReview(userId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/places/{placeId}")
    fun createPlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeId: Long,
        @RequestBody request: CreatePlaceReviewRequest,
    ): ResponseEntity<CreatePlaceReviewResponse> {
        val response = placeReviewCommandFacade.createPlaceReview(userId = userId, placeId = placeId, request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PatchMapping("/{placeReviewId}")
    fun updatePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
        @RequestBody request: UpdatePlaceReviewRequest,
    ): ResponseEntity<Unit> {
        placeReviewCommandFacade.updatePlaceReview(userId = userId, placeReviewId = placeReviewId, request = request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{placeReviewId}")
    fun deletePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<Unit> {
        placeReviewCommandFacade.deletePlaceReview(userId = userId, placeReviewId = placeReviewId)
        return ResponseEntity.ok().build()
    }
}

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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reviews")
class PlaceReviewController(
    private val placeReviewQueryFacade: PlaceReviewQueryFacade,
    private val placeReviewCommandFacade: PlaceReviewCommandFacade,
) : PlaceReviewApi {
    @GetMapping("/{placeReviewId}")
    override fun getPlaceReviewDetail(
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<PlaceReviewDetailsResponse> {
        val response = placeReviewQueryFacade.getPlaceReviewDetail(placeReviewId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/places/{placeId}")
    override fun getAllPlaceReviews(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>> {
        val response = placeReviewQueryFacade.getAllPlaceReviewDetail(placeId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}")
    override fun getAllMyPlaceReviews(
        @PathVariable userId: Long,
    ): ResponseEntity<List<PlaceReviewDetailsResponse>> {
        val response = placeReviewQueryFacade.getAllMyPlaceReview(userId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/places/{placeId}")
    override fun createPlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeId: Long,
        @RequestBody request: CreatePlaceReviewRequest,
    ): ResponseEntity<CreatePlaceReviewResponse> {
        val response = placeReviewCommandFacade.createPlaceReview(userId = userId, placeId = placeId, request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PatchMapping("/{placeReviewId}")
    override fun updatePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
        @RequestBody request: UpdatePlaceReviewRequest,
    ): ResponseEntity<Unit> {
        placeReviewCommandFacade.updatePlaceReview(userId = userId, placeReviewId = placeReviewId, request = request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{placeReviewId}")
    override fun deletePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<Unit> {
        placeReviewCommandFacade.deletePlaceReview(userId = userId, placeReviewId = placeReviewId)
        return ResponseEntity.ok().build()
    }
}

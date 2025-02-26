package kr.wooco.woocobe.api.placereview

import kr.wooco.woocobe.api.placereview.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.api.placereview.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.api.placereview.response.CreatePlaceReviewResponse
import kr.wooco.woocobe.api.placereview.response.PlaceReviewWithPlaceDetailsResponse
import kr.wooco.woocobe.api.placereview.response.PlaceReviewWithWriterDetailsResponse
import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllMyPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
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
    private val createPlaceReviewUseCase: CreatePlaceReviewUseCase,
    private val updatePlaceReviewUseCase: UpdatePlaceReviewUseCase,
    private val deletePlaceReviewUseCase: DeletePlaceReviewUseCase,
    private val readAllMyPlaceReviewUseCase: ReadAllMyPlaceReviewUseCase,
    private val readAllPlaceReviewUseCase: ReadAllPlaceReviewUseCase,
    private val readPlaceReviewUseCase: ReadPlaceReviewUseCase,
) : PlaceReviewApi {
    @GetMapping("/{placeReviewId}")
    override fun getPlaceReviewDetail(
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<PlaceReviewWithWriterDetailsResponse> {
        val query = ReadPlaceReviewUseCase.Query(placeReviewId)
        val results = readPlaceReviewUseCase.readPlaceReview(query)
        return ResponseEntity.ok(PlaceReviewWithWriterDetailsResponse.from(results))
    }

    @GetMapping("/places/{placeId}")
    override fun getAllPlaceReview(
        @PathVariable placeId: Long,
    ): ResponseEntity<List<PlaceReviewWithWriterDetailsResponse>> {
        val query = ReadAllPlaceReviewUseCase.Query(placeId)
        val results = readAllPlaceReviewUseCase.readAllPlaceReview(query)
        return ResponseEntity.ok(PlaceReviewWithWriterDetailsResponse.listFrom(results))
    }

    @GetMapping("/users/{userId}")
    override fun getAllMyPlaceReview(
        @PathVariable userId: Long,
    ): ResponseEntity<List<PlaceReviewWithPlaceDetailsResponse>> {
        val query = ReadAllMyPlaceReviewUseCase.Query(userId)
        val results = readAllMyPlaceReviewUseCase.readAllMyPlaceReview(query)
        return ResponseEntity.ok(PlaceReviewWithPlaceDetailsResponse.listFrom(results))
    }

    @PostMapping("/places/{placeId}")
    override fun createPlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeId: Long,
        @RequestBody request: CreatePlaceReviewRequest,
    ): ResponseEntity<CreatePlaceReviewResponse> {
        val command = request.toCommand(userId, placeId)
        val results = createPlaceReviewUseCase.createPlaceReview(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePlaceReviewResponse(results))
    }

    @PatchMapping("/{placeReviewId}")
    override fun updatePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
        @RequestBody request: UpdatePlaceReviewRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId, placeReviewId)
        updatePlaceReviewUseCase.updatePlaceReview(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{placeReviewId}")
    override fun deletePlaceReview(
        @AuthenticationPrincipal userId: Long,
        @PathVariable placeReviewId: Long,
    ): ResponseEntity<Unit> {
        val command = DeletePlaceReviewUseCase.Command(userId, placeReviewId)
        deletePlaceReviewUseCase.deletePlaceReview(command)
        return ResponseEntity.ok().build()
    }
}

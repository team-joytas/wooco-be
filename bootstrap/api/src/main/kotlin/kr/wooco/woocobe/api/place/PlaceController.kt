package kr.wooco.woocobe.api.place

import kr.wooco.woocobe.api.place.request.CreatePlaceRequest
import kr.wooco.woocobe.api.place.response.CreatePlaceResponse
import kr.wooco.woocobe.api.place.response.PlaceDetailResponse
import kr.wooco.woocobe.api.place.response.PlaceDetailWithPlaceReviewsResponse
import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceWithPlaceReviewsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/places")
class PlaceController(
    private val readPlaceWithPlaceReviewsUseCase: ReadPlaceWithPlaceReviewsUseCase,
    private val readPlaceUseCase: ReadPlaceUseCase,
    private val createPlaceIfNotExistsUseCase: CreatePlaceIfNotExistsUseCase,
) : PlaceApi {
    @GetMapping("/{placeId}")
    override fun getPlaceDetail(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceDetailResponse> {
        val query = ReadPlaceUseCase.Query(placeId)
        val results = readPlaceUseCase.readPlace(query)
        return ResponseEntity.ok(PlaceDetailResponse.from(results))
    }

    @PostMapping
    override fun createPlace(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlaceRequest,
    ): ResponseEntity<CreatePlaceResponse> {
        val command = request.toCommand()
        val result = createPlaceIfNotExistsUseCase.createPlaceIfNotExists(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePlaceResponse(result))
    }

    @GetMapping("/{placeId}/aggregation")
    override fun getPlaceDetailWithPlaceReview(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceDetailWithPlaceReviewsResponse> {
        val query = ReadPlaceWithPlaceReviewsUseCase.Query(placeId)
        val results = readPlaceWithPlaceReviewsUseCase.readPlaceWithPlaceReviews(query)
        return ResponseEntity.ok(PlaceDetailWithPlaceReviewsResponse.from(results))
    }
}

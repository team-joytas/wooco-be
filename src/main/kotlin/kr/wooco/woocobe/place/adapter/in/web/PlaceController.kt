package kr.wooco.woocobe.place.adapter.`in`.web

import kr.wooco.woocobe.place.adapter.`in`.web.request.CreatePlaceRequest
import kr.wooco.woocobe.place.adapter.`in`.web.response.CreatePlaceResponse
import kr.wooco.woocobe.place.adapter.`in`.web.response.PlaceDetailResponse
import kr.wooco.woocobe.place.application.port.`in`.CreatePlaceUseCase
import kr.wooco.woocobe.place.application.port.`in`.ReadPlaceUseCase
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
    private val createPlaceUseCase: CreatePlaceUseCase,
    private val readPlaceUseCase: ReadPlaceUseCase,
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
        val result = createPlaceUseCase.createPlace(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePlaceResponse(result))
    }
}

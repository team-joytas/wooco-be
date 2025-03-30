package kr.wooco.woocobe.api.place

import kr.wooco.woocobe.api.common.security.LoginRequired
import kr.wooco.woocobe.api.place.request.CreatePlaceRequest
import kr.wooco.woocobe.api.place.response.CreatePlaceResponse
import kr.wooco.woocobe.api.place.response.PlaceDetailResponse
import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceUseCase
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
    private val createPlaceIfNotExistsUseCase: CreatePlaceIfNotExistsUseCase,
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

    @LoginRequired
    @PostMapping
    override fun createPlace(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlaceRequest,
    ): ResponseEntity<CreatePlaceResponse> {
        val command = request.toCommand()
        val result = createPlaceIfNotExistsUseCase.createPlaceIfNotExists(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePlaceResponse(result))
    }
}

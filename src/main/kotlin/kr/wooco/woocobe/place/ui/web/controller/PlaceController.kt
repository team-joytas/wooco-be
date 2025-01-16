package kr.wooco.woocobe.place.ui.web.controller

import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceDetailResponse
import kr.wooco.woocobe.place.ui.web.facade.PlaceCommandFacade
import kr.wooco.woocobe.place.ui.web.facade.PlaceQueryFacade
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
    private val placeQueryFacade: PlaceQueryFacade,
    private val placeCommandFacade: PlaceCommandFacade,
) : PlaceApi {
    @GetMapping("/{placeId}")
    override fun getPlaceDetail(
        @PathVariable placeId: Long,
    ): ResponseEntity<PlaceDetailResponse> {
        val response = placeQueryFacade.getPlaceDetail(placeId = placeId)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    override fun createPlace(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreatePlaceRequest,
    ): ResponseEntity<CreatePlaceResponse> {
        val response = placeCommandFacade.createPlace(userId = userId, request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}

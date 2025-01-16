package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.AddPlaceUseCase
import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceResponse
import org.springframework.stereotype.Service

@Service
class PlaceCommandFacade(
    private val addPlaceUseCase: AddPlaceUseCase,
) {
    fun createPlace(request: CreatePlaceRequest): CreatePlaceResponse {
        val createPlaceResult = addPlaceUseCase.execute(request.toCommand())
        return CreatePlaceResponse(createPlaceResult.placeId)
    }
}

package kr.wooco.woocobe.place.ui.web.controller.request

import kr.wooco.woocobe.place.domain.usecase.AddPlaceUseCaseInput

data class CreatePlaceRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
) {
    fun toCommand(userId: Long): AddPlaceUseCaseInput =
        AddPlaceUseCaseInput(
            userId = userId,
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoMapPlaceId = kakaoMapPlaceId,
        )
}

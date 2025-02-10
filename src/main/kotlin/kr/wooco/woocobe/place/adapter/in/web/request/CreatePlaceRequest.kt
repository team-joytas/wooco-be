package kr.wooco.woocobe.place.adapter.`in`.web.request

import kr.wooco.woocobe.place.application.port.`in`.CreatePlaceUseCase

data class CreatePlaceRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    val phoneNumber: String,
) {
    fun toCommand(): CreatePlaceUseCase.Command =
        CreatePlaceUseCase.Command(
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoPlaceId = kakaoPlaceId,
            phoneNumber = phoneNumber,
        )
}

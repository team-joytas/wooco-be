package kr.wooco.woocobe.api.place.request

import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase

data class CreatePlaceRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    val phoneNumber: String,
) {
    fun toCommand(): CreatePlaceIfNotExistsUseCase.Command =
        CreatePlaceIfNotExistsUseCase.Command(
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoPlaceId = kakaoPlaceId,
            phoneNumber = phoneNumber,
        )
}

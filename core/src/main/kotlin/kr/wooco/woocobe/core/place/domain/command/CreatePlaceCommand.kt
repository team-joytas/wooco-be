package kr.wooco.woocobe.core.place.domain.command

data class CreatePlaceCommand(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    val phoneNumber: String,
)

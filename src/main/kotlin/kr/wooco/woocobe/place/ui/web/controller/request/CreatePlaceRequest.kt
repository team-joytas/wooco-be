package kr.wooco.woocobe.place.ui.web.controller.request

data class CreatePlaceRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
)

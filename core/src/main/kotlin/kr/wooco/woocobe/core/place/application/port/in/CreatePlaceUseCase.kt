package kr.wooco.woocobe.core.place.application.port.`in`

fun interface CreatePlaceUseCase {
    data class Command(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val kakaoPlaceId: String,
        val phoneNumber: String,
    )

    fun createPlace(command: Command): Long
}

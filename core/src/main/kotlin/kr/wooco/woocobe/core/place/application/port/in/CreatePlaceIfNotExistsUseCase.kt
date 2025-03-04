package kr.wooco.woocobe.core.place.application.port.`in`

fun interface CreatePlaceIfNotExistsUseCase {
    data class Command(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val kakaoPlaceId: String,
        val phoneNumber: String,
    )

    fun createPlaceIfNotExists(command: Command): Long
}

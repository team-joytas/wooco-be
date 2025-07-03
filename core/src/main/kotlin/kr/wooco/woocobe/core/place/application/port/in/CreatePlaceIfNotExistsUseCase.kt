package kr.wooco.woocobe.core.place.application.port.`in`

import kr.wooco.woocobe.core.place.domain.command.CreatePlaceCommand

fun interface CreatePlaceIfNotExistsUseCase {
    data class Command(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val kakaoPlaceId: String,
        val phoneNumber: String,
    ) {
        fun toCreateCommand(): CreatePlaceCommand =
            CreatePlaceCommand(
                name = name,
                latitude = latitude,
                longitude = longitude,
                address = address,
                kakaoPlaceId = kakaoPlaceId,
                phoneNumber = phoneNumber,
            )
    }

    fun createPlaceIfNotExists(command: Command): Long
}

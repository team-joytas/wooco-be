package kr.wooco.woocobe.core.place.application.port.`in`

fun interface UpdatePlaceImageUseCase {
    data class Command(
        val placeId: Long,
    )

    fun updatePlaceImage(command: Command)
}

package kr.wooco.woocobe.core.place.application.port.`in`

fun interface RefreshPlaceThumbnailUseCase {
    data class Command(
        val placeId: Long,
    )

    fun refreshPlaceThumbnail(command: Command)
}

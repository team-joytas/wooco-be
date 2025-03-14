package kr.wooco.woocobe.core.place.application.port.`in`

fun interface ProcessAverageRatingUseCase {
    data class Command(
        val placeId: Long,
    )

    fun processAverageRating(command: Command)
}

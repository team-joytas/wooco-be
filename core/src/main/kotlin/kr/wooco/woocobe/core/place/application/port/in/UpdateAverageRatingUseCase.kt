package kr.wooco.woocobe.core.place.application.port.`in`

fun interface UpdateAverageRatingUseCase {
    data class Command(
        val placeId: Long,
    )

    fun updateAverageRating(command: Command)
}

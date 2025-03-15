package kr.wooco.woocobe.core.place.application.port.`in`

fun interface IncreasePlaceReviewCountUseCase {
    data class Command(
        val placeId: Long,
    )

    fun increasePlaceReviewCount(command: Command)
}

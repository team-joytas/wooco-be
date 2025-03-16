package kr.wooco.woocobe.core.place.application.port.`in`

fun interface UpdateReviewStatsUseCase {
    data class Command(
        val placeId: Long,
    )

    fun updateReviewStats(command: Command)
}

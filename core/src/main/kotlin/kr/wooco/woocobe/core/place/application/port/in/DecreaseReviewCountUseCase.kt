package kr.wooco.woocobe.core.place.application.port.`in`

interface DecreaseReviewCountUseCase {
    data class Command(
        val placeId: Long,
    )

    fun decreaseReviewCount(command: Command)
}

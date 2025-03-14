package kr.wooco.woocobe.core.placereview.application.port.`in`

fun interface ExistsPlaceReviewWriterUseCase {
    data class Query(
        val placeId: Long,
        val userId: Long,
    )

    fun existsPlaceReviewWriter(query: Query): Boolean
}

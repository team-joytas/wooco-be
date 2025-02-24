package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewResult

fun interface ReadAllMyPlaceReviewUseCase {
    data class Query(
        val userId: Long,
    )

    fun readAllMyPlaceReview(query: Query): List<PlaceReviewResult>
}

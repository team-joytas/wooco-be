package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewResult

fun interface ReadPlaceReviewUseCase {
    data class Query(
        val placeReviewId: Long,
    )

    fun readPlaceReview(query: Query): PlaceReviewResult
}

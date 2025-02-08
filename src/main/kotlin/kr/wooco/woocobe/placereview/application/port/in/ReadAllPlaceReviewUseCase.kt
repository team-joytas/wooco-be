package kr.wooco.woocobe.placereview.application.port.`in`

import kr.wooco.woocobe.placereview.application.port.`in`.result.PlaceReviewResult

fun interface ReadAllPlaceReviewUseCase {
    data class Query(
        val placeId: Long,
    )

    fun readAllPlaceReview(query: Query): List<PlaceReviewResult>
}

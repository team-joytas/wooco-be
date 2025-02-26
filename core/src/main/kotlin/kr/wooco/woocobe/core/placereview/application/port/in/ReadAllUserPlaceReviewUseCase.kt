package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithPlaceResult

fun interface ReadAllUserPlaceReviewUseCase {
    data class Query(
        val userId: Long,
    )

    fun readAllUserPlaceReview(query: Query): List<PlaceReviewWithPlaceResult>
}

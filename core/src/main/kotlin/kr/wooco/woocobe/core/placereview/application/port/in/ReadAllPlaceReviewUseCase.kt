package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithWriterResult

fun interface ReadAllPlaceReviewUseCase {
    data class Query(
        val placeId: Long,
    )

    fun readAllPlaceReview(query: Query): List<PlaceReviewWithWriterResult>
}

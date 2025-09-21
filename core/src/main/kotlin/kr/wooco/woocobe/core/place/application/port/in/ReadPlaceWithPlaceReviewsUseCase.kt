package kr.wooco.woocobe.core.place.application.port.`in`

import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceWithPlaceReviewsResult

fun interface ReadPlaceWithPlaceReviewsUseCase {
    data class Query(
        val placeId: Long,
    )

    fun readPlaceWithPlaceReviews(query: Query): PlaceWithPlaceReviewsResult
}

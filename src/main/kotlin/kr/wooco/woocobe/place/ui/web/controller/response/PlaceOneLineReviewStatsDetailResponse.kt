package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

data class PlaceOneLineReviewStatsDetailResponse(
    val content: String,
    val count: Long,
) {
    companion object {
        fun listFrom(placeOneLineReviewStat: List<PlaceOneLineReviewStat>): List<PlaceOneLineReviewStatsDetailResponse> =
            placeOneLineReviewStat.map {
                PlaceOneLineReviewStatsDetailResponse(
                    content = it.content,
                    count = it.count,
                )
            }
    }
}

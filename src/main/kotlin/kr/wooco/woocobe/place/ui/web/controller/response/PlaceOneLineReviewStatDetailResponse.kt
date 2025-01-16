package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

data class PlaceOneLineReviewStatDetailResponse(
    val content: String,
    val count: Long,
) {
    companion object {
        fun listFrom(placeOneLineReviewStat: List<PlaceOneLineReviewStat>): List<PlaceOneLineReviewStatDetailResponse> =
            placeOneLineReviewStat.map {
                PlaceOneLineReviewStatDetailResponse(
                    content = it.content,
                    count = it.count,
                )
            }
    }
}

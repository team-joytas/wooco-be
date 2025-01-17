package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

data class PlaceOneLineReviewStatDetailResponse(
    val contents: String,
    val count: Long,
) {
    companion object {
        fun listFrom(placeOneLineReviewStat: List<PlaceOneLineReviewStat>): List<PlaceOneLineReviewStatDetailResponse> =
            placeOneLineReviewStat.map {
                PlaceOneLineReviewStatDetailResponse(
                    contents = it.contents,
                    count = it.count,
                )
            }
    }
}

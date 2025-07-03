package kr.wooco.woocobe.core.placereview.domain.command

import kr.wooco.woocobe.core.placereview.domain.vo.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

data class CreatePlaceReviewCommand(
    val userId: Long,
    val placeId: Long,
    val rating: PlaceReviewRating,
    val contents: String,
    val oneLineReviews: List<PlaceOneLineReview>,
    val imageUrls: List<String>,
)

package kr.wooco.woocobe.core.placereview.domain.command

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

data class CreatePlaceReviewCommand(
    val userId: Long,
    val placeId: Long,
    val rating: PlaceReviewRating,
    val contents: PlaceReview.Contents,
    val oneLineReviews: List<PlaceReview.OneLineReview>,
    val imageUrls: List<String>,
)

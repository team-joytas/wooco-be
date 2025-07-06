package kr.wooco.woocobe.core.placereview.domain.command

import kr.wooco.woocobe.core.placereview.domain.vo.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewContent
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

data class UpdatePlaceReviewCommand(
    val userId: Long,
    val rating: PlaceReviewRating,
    val contents: PlaceReviewContent,
    val oneLineReviews: List<PlaceOneLineReview>,
    val imageUrls: List<String>,
)

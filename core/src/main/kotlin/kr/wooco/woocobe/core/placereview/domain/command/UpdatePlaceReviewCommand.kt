package kr.wooco.woocobe.core.placereview.domain.command

import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

data class UpdatePlaceReviewCommand(
    val userId: Long,
    val rating: PlaceReviewRating,
    val content: String,
    val imageUrls: List<String>,
)

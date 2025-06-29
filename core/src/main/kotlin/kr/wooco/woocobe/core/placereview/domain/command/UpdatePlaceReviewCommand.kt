package kr.wooco.woocobe.core.placereview.domain.command

import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

data class UpdatePlaceReviewCommand(
    val userId: Long,
    val rating: PlaceReviewRating,
    val contents: String,
    val imageUrls: List<String>,
)

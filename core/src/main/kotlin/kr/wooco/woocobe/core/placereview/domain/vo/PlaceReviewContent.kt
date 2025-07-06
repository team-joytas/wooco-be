package kr.wooco.woocobe.core.placereview.domain.vo

data class PlaceReviewContent(
    val contents: String,
) {
    init {
        require(contents.isNotBlank()) { "리뷰 본문 내용이 없습니다." }
    }
}

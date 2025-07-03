package kr.wooco.woocobe.core.placereview.domain.vo

data class PlaceOneLineReview(
    val contents: String,
) {
    init {
        require(contents.isNotBlank()) { "한줄평 내용이 없습니다." }
        require(contents.length <= 7) { "한줄평 내용은 7자를 넘을 수 없습니다." }
    }
}

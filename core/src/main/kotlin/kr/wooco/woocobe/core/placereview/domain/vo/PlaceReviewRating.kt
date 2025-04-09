package kr.wooco.woocobe.core.placereview.domain.vo

enum class PlaceReviewRating(
    val score: Double,
) {
    ONE(1.0),
    TWO(2.0),
    THREE(3.0),
    FOUR(4.0),
    FIVE(5.0),
    ;

    companion object {
        operator fun invoke(score: Double): PlaceReviewRating = entries.first { it.score == score }
    }
}

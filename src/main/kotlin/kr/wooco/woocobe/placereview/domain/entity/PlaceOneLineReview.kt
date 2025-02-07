package kr.wooco.woocobe.placereview.domain.entity

class PlaceOneLineReview(
    val contents: String,
) {
    companion object {
        fun from(contents: String): PlaceOneLineReview =
            PlaceOneLineReview(
                contents = contents,
            )
    }
}

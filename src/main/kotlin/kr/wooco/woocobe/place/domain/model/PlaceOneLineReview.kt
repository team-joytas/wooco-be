package kr.wooco.woocobe.place.domain.model

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

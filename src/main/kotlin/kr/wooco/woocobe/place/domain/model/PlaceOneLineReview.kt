package kr.wooco.woocobe.place.domain.model

class PlaceOneLineReview(
    val content: String,
) {
    companion object {
        fun from(content: String): PlaceOneLineReview =
            PlaceOneLineReview(
                content = content,
            )
    }
}

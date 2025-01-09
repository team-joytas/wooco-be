package kr.wooco.woocobe.place.domain.model

import java.time.LocalDateTime

class PlaceReview(
    val id: Long,
    val userId: Long,
    val placeId: Long,
    val writeDateTime: LocalDateTime,
    var rating: Double,
    var content: String,
    var oneLineReviews: List<PlaceOneLineReview>,
    var imageUrls: List<String>,
) {
    fun update(
        rating: Double,
        content: String,
        oneLineReviews: List<String>,
        imageUrls: List<String>,
    ) = apply {
        this.rating = rating
        this.content = content
        this.oneLineReviews = oneLineReviews.map { PlaceOneLineReview.from(it) }
        this.imageUrls = imageUrls
    }

    fun isValidWriter(userId: Long) {
        if (this.userId != userId) {
            throw RuntimeException()
        }
    }

    companion object {
        fun register(
            userId: Long,
            placeId: Long,
            rating: Double,
            content: String,
            oneLineReview: List<String>,
            imageUrls: List<String>,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                userId = userId,
                placeId = placeId,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                content = content,
                oneLineReviews = oneLineReview.map { PlaceOneLineReview.from(it) },
                imageUrls = imageUrls,
            )
    }
}

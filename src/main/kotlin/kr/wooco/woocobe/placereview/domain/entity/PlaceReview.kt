package kr.wooco.woocobe.placereview.domain.entity

import kr.wooco.woocobe.placereview.domain.exception.InvalidPlaceReviewWriterException
import java.time.LocalDateTime

class PlaceReview(
    val id: Long,
    val userId: Long,
    val placeId: Long,
    val writeDateTime: LocalDateTime,
    var rating: Double,
    var contents: String,
    var oneLineReviews: List<PlaceOneLineReview>,
    var imageUrls: List<String>,
) {
    fun update(
        userId: Long,
        rating: Double,
        contents: String,
        oneLineReviews: List<String>,
        imageUrls: List<String>,
    ) = apply {
        isValidWriter(userId)

        this.rating = rating
        this.contents = contents
        this.oneLineReviews = oneLineReviews.map { PlaceOneLineReview.from(it) }
        this.imageUrls = imageUrls
    }

    fun isValidWriter(userId: Long) {
        if (this.userId != userId) {
            throw InvalidPlaceReviewWriterException
        }
    }

    companion object {
        fun create(
            userId: Long,
            placeId: Long,
            rating: Double,
            contents: String,
            oneLineReviews: List<String>,
            imageUrls: List<String>,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                userId = userId,
                placeId = placeId,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                contents = contents,
                oneLineReviews = oneLineReviews.map { PlaceOneLineReview.from(it) },
                imageUrls = imageUrls,
            )
    }
}

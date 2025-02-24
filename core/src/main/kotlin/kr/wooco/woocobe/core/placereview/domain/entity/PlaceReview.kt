package kr.wooco.woocobe.core.placereview.domain.entity

import kr.wooco.woocobe.core.placereview.domain.exception.InvalidPlaceReviewWriterException
import java.time.LocalDateTime

data class PlaceReview(
    val id: Long,
    val userId: Long,
    val placeId: Long,
    val writeDateTime: LocalDateTime,
    val rating: Double,
    val contents: String,
    val imageUrls: List<String>,
) {
    fun update(
        userId: Long,
        rating: Double,
        contents: String,
        imageUrls: List<String>,
    ): PlaceReview {
        isValidWriter(userId)
        return copy(
            rating = rating,
            contents = contents,
            imageUrls = imageUrls,
        )
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
            imageUrls: List<String>,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                userId = userId,
                placeId = placeId,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                contents = contents,
                imageUrls = imageUrls,
            )
    }
}

package kr.wooco.woocobe.place.domain.model

import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

class PlaceReview(
    val id: Long,
    val user: User,
    val place: Place,
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

    fun isWriter(targetId: Long): Boolean = user.id == targetId

    companion object {
        fun register(
            user: User,
            place: Place,
            rating: Double,
            content: String,
            oneLineReview: List<PlaceOneLineReview>,
            imageUrls: List<String>,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                user = user,
                place = place,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                content = content,
                oneLineReviews = oneLineReview,
                imageUrls = imageUrls,
            )
    }
}

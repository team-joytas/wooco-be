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
    var imageUrl: String,
) {
    fun update(
        rating: Double,
        content: String,
        oneLineReviews: List<String>,
        imageUrl: String,
    ) = apply {
        this.rating = rating
        this.content = content
        this.oneLineReviews = oneLineReviews.map { PlaceOneLineReview.from(it) }
        this.imageUrl = imageUrl
    }

    fun isWriter(targetId: Long): Boolean =
        when (user.id == targetId) {
            true -> true
            else -> throw RuntimeException()
        }

    companion object {
        fun register(
            user: User,
            place: Place,
            rating: Double,
            content: String,
            oneLineReview: List<PlaceOneLineReview>,
            imageUrl: String,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                user = user,
                place = place,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                content = content,
                oneLineReviews = oneLineReview.map { it },
                imageUrl = imageUrl,
            )
    }
}

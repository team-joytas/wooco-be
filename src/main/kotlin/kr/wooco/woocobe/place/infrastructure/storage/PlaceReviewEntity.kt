package kr.wooco.woocobe.place.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReview
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "place_reviews")
class PlaceReviewEntity(
    @Column(name = "content", nullable = false)
    val content: String,
    @Column(name = "rating", nullable = false)
    val rating: Double,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "place_id", nullable = false)
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_review_id", nullable = false)
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(
        user: User,
        place: Place,
        placeOneLineReview: List<PlaceOneLineReview> = emptyList(),
        imageUrls: List<String> = emptyList(),
    ): PlaceReview =
        PlaceReview(
            id = id!!,
            user = user,
            place = place,
            writeDateTime = createdAt,
            rating = rating,
            content = content,
            oneLineReviews = placeOneLineReview.map { PlaceOneLineReview.from(it.content) },
            imageUrls = imageUrls,
        )

    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewEntity =
            with(placeReview) {
                PlaceReviewEntity(
                    id = id,
                    userId = user.id,
                    placeId = place.id,
                    content = content,
                    rating = rating,
                )
            }
    }
}

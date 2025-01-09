package kr.wooco.woocobe.place.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReview
import kr.wooco.woocobe.place.domain.model.PlaceReview

@Entity
@Table(name = "place_reviews")
class PlaceReviewJpaEntity(
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
        place: Place,
        placeOneLineReview: List<PlaceOneLineReview> = emptyList(),
        imageUrls: List<String> = emptyList(),
    ): PlaceReview =
        PlaceReview(
            id = id!!,
            userId = userId,
            placeId = place.id,
            writeDateTime = createdAt,
            rating = rating,
            content = content,
            oneLineReviews = placeOneLineReview.map { PlaceOneLineReview.from(it.content) },
            imageUrls = imageUrls,
        )

    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewJpaEntity =
            with(placeReview) {
                PlaceReviewJpaEntity(
                    id = id,
                    userId = userId,
                    placeId = placeId,
                    content = content,
                    rating = rating,
                )
            }
    }
}

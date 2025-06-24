package kr.wooco.woocobe.mysql.placereview.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "place_reviews")
data class PlaceReviewJpaEntity(
    @Column(name = "placeReview_status")
    val status: String,
    @Column(name = "contents")
    val content: String,
    @Column(name = "rating")
    val rating: Double,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_review_id")
    override val id: Long = 0L,
) : BaseTimeEntity() {
    fun applyUpdate(placeReview: PlaceReview): PlaceReviewJpaEntity =
        copy(
            rating = placeReview.rating.score,
            content = placeReview.content,
            status = placeReview.status.name,
        )

    companion object {
        fun create(placeReview: PlaceReview): PlaceReviewJpaEntity =
            PlaceReviewJpaEntity(
                placeId = placeReview.placeId,
                userId = placeReview.userId,
                rating = placeReview.rating.score,
                content = placeReview.content,
                status = placeReview.status.name,
            )
    }
}

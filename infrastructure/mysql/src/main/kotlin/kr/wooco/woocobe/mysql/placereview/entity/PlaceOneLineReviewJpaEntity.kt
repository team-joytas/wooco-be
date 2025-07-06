package kr.wooco.woocobe.mysql.placereview.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "place_one_line_reviews")
class PlaceOneLineReviewJpaEntity(
    @Column(name = "contents")
    val contents: String,
    @Column(name = "place_review_id")
    val placeReviewId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_one_line_review_id")
    override val id: Long = 0L,
) : BaseTimeEntity() {
    companion object {
        fun listOf(
            placeReview: PlaceReview,
            placeReviewJpaEntity: PlaceReviewJpaEntity,
        ): List<PlaceOneLineReviewJpaEntity> =
            placeReview.oneLineReviews.map { oneLineReview ->
                PlaceOneLineReviewJpaEntity(
                    placeId = placeReviewJpaEntity.placeId,
                    placeReviewId = placeReviewJpaEntity.id,
                    contents = oneLineReview.value,
                )
            }
    }
}

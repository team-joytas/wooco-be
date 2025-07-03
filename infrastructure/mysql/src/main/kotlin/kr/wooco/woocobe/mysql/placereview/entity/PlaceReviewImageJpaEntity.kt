package kr.wooco.woocobe.mysql.placereview.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "place_review_images")
class PlaceReviewImageJpaEntity(
    @Column(name = "image_url")
    val imageUrl: String,
    @Column(name = "place_id")
    val placeReviewId: Long,
    @Id @Tsid
    @Column(name = "place_review_images_id")
    override val id: Long = 0L,
) : BaseTimeEntity() {
    companion object {
        fun listOf(
            placeReview: PlaceReview,
            placeReviewJpaEntity: PlaceReviewJpaEntity,
        ): List<PlaceReviewImageJpaEntity> =
            placeReview.imageUrls.map { imageUrl ->
                PlaceReviewImageJpaEntity(
                    placeReviewId = placeReviewJpaEntity.id,
                    imageUrl = imageUrl,
                )
            }
    }
}

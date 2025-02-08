package kr.wooco.woocobe.placereview.adapter.out.presistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

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
) : BaseTimeEntity()

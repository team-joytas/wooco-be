package kr.wooco.woocobe.place.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "place_review_images")
class PlaceReviewImageEntity(
    @Column(name = "image_url")
    val imageUrl: String,
    @Column(name = "place_id")
    val placeReviewId: Long,
    @Id @Tsid
    @Column(name = "place_review_images_id")
    val id: Long? = 0L,
)

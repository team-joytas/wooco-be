package kr.wooco.woocobe.place.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "place_reviews")
class PlaceReviewJpaEntity(
    @Column(name = "contents", nullable = false)
    val contents: String,
    @Column(name = "rating", nullable = false)
    val rating: Double,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "place_id", nullable = false)
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_review_id", nullable = false)
    override val id: Long = 0L,
) : BaseTimeEntity()

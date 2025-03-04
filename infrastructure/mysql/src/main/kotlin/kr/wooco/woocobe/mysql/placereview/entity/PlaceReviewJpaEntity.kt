package kr.wooco.woocobe.mysql.placereview.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "place_reviews")
class PlaceReviewJpaEntity(
    @Column(name = "contents")
    val contents: String,
    @Column(name = "rating")
    val rating: Double,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_review_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

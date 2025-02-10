package kr.wooco.woocobe.placereview.adapter.out.presistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "place_one_line_review_stats")
class PlaceOneLineReviewStatJpaEntity(
    @Column(name = "count")
    val count: Long,
    @Column(name = "contents")
    val contents: String,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "place_one_line_review_stat_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

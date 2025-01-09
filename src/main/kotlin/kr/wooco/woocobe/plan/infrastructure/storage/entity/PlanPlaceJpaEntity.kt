package kr.wooco.woocobe.plan.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "plan_places")
class PlanPlaceJpaEntity(
    @Column(name = "plan_place_order")
    val order: Int,
    @Column(name = "plan_id")
    val planId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    override val id: Long = 0L,
) : BaseTimeEntity()

package kr.wooco.woocobe.plan.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "plans")
class PlanJpaEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "title", length = 20)
    val title: String,
    @Column(name = "description", columnDefinition = "text")
    val description: String,
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Id @Tsid
    @Column(name = "plan_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

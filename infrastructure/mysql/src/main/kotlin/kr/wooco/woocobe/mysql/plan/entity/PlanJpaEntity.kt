package kr.wooco.woocobe.mysql.plan.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid
import java.time.LocalDate

@Entity
@Table(name = "plans")
class PlanJpaEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "title", length = 20)
    val title: String,
    @Column(name = "contents", columnDefinition = "text")
    val contents: String,
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "is_shared")
    val isShared: Boolean,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Id @Tsid
    @Column(name = "plan_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

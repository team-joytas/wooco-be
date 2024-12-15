package kr.wooco.woocobe.plan.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanRegionInfo
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import java.time.LocalDate

@Entity
@Table(name = "plans")
class PlanEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Id
    @Column(name = "plan_id")
    val id: Long,
) : BaseTimeEntity() {
    fun toDomain(): Plan =
        Plan(
            id = id,
            writer = user.toDomain(),
            regionInfo = PlanRegionInfo.of(primaryRegion, secondaryRegion),
            visitDate = visitDate,
        )

    companion object {
        fun from(plan: Plan): PlanEntity =
            with(plan) {
                PlanEntity(
                    id = id,
                    user = UserEntity.from(plan.writer),
                    primaryRegion = regionInfo.primaryRegion,
                    secondaryRegion = regionInfo.secondaryRegion,
                    visitDate = visitDate,
                )
            }
    }
}

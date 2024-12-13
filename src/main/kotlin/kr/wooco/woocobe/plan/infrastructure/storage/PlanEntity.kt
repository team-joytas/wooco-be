package kr.wooco.woocobe.plan.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanRegionInfo
import java.time.LocalDate

@Entity
@Table(name = "plans")
class PlanEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "major_region")
    val majorRegion: String,
    @Column(name = "sub_region")
    val subRegion: String,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Id
    @Column(name = "plan_id")
    val id: Long,
) : BaseTimeEntity() {
    fun toDomain(): Plan =
        Plan(
            id = id,
            userId = userId,
            regionInfo = PlanRegionInfo.register(majorRegion, subRegion),
            visitDate = visitDate,
        )

    companion object {
        fun from(plan: Plan): PlanEntity =
            with(plan) {
                PlanEntity(
                    id = id,
                    userId = userId,
                    majorRegion = regionInfo.majorRegion,
                    subRegion = regionInfo.subRegion,
                    visitDate = visitDate,
                )
            }
    }
}

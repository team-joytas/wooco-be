package kr.wooco.woocobe.plan.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanRegion
import java.time.LocalDate

@Entity
@Table(name = "plans")
class PlanEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Id @Tsid
    @Column(name = "plan_id")
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(): Plan =
        Plan(
            id = id!!,
            userId = userId,
            region = PlanRegion.register(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            ),
            visitDate = visitDate,
        )

    companion object {
        fun from(plan: Plan): PlanEntity =
            with(plan) {
                PlanEntity(
                    id = id,
                    userId = userId,
                    primaryRegion = region.primaryRegion,
                    secondaryRegion = region.secondaryRegion,
                    visitDate = visitDate,
                )
            }
    }
}

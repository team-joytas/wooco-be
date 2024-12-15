package kr.wooco.woocobe.plan.domain.model

import kr.wooco.woocobe.common.domain.IdGenerator
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDate

// TODO: 장소 정보 추가
class Plan(
    val id: Long,
    val writer: User,
    val regionInfo: PlanRegionInfo,
    val visitDate: LocalDate,
) {
    companion object {
        fun register(
            user: User,
            regionInfo: PlanRegionInfo,
            visitDate: LocalDate,
        ): Plan =
            Plan(
                id = IdGenerator.generateId(),
                writer = user,
                regionInfo = regionInfo,
                visitDate = visitDate,
            )
    }

    fun isWriter(writerId: Long): Boolean = writerId == writer.id

    fun withUpdatedValues(
        regionInfo: PlanRegionInfo?,
        visitDate: LocalDate?,
    ): Plan =
        Plan(
            id = this.id,
            writer = this.writer,
            regionInfo = regionInfo ?: this.regionInfo,
            visitDate = visitDate ?: this.visitDate,
        )
}

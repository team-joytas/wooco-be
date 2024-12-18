package kr.wooco.woocobe.plan.domain.model

import kr.wooco.woocobe.common.domain.IdGenerator
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDate

// TODO: 장소 정보 추가
class Plan(
    val id: Long,
    val user: User,
    var region: PlanRegion,
    var visitDate: LocalDate,
) {
    fun isWriter(targetId: Long): Boolean = user.id == targetId

    fun update(
        region: PlanRegion,
        visitDate: LocalDate,
    ) = apply {
        this.region = region
        this.visitDate = visitDate
    }

    // FIXME: 아이디 생성 책임을 storage에 위임
    companion object {
        fun register(
            user: User,
            region: PlanRegion,
            visitDate: LocalDate,
        ): Plan =
            Plan(
                id = IdGenerator.generateId(),
                user = user,
                region = region,
                visitDate = visitDate,
            )
    }
}

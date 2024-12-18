package kr.wooco.woocobe.plan.domain.model

import kr.wooco.woocobe.common.domain.IdGenerator
import kr.wooco.woocobe.user.domain.model.User

// TODO: 장소 정보 추가
class Plan(
    val id: Long,
    val user: User,
    var region: PlanRegion,
    var visitDate: PlanDate,
) {
    fun isWriter(targetId: Long): Boolean = user.id == targetId

    fun update(
        region: PlanRegion,
        visitDate: PlanDate,
    ) = apply {
        this.region = region
        this.visitDate = visitDate
    }

    // FIXME: 아이디 생성 책임을 storage에 위임
    companion object {
        fun register(
            user: User,
            region: PlanRegion,
            visitDate: PlanDate,
        ): Plan =
            Plan(
                id = IdGenerator.generateId(),
                user = user,
                region = region,
                visitDate = visitDate,
            )
    }
}

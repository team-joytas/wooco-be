package kr.wooco.woocobe.core.calendar.schedule.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.DomainEntity

data class PlanPlace(
    override val id: Long,
    val order: Int,
    val placeId: Long,
): DomainEntity() {
    internal fun replaceOrder(order: Int): PlanPlace =
        copy(
            order = order,
        )

    companion object {
        internal fun create(
            order: Int,
            placeId: Long,
        ): PlanPlace =
            PlanPlace(
                id = 0L,
                order = order,
                placeId = placeId,
            )
    }
}

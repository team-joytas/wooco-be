package kr.wooco.woocobe.core.course.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.DomainEntity

data class CoursePlace(
    override val id: Long, // localize identifier
    val placeId: Long,
    val order: Int,
) : DomainEntity() {
    internal fun replaceOrder(order: Int): CoursePlace =
        copy(
            order = order,
        )

    companion object {
        internal fun create(
            order: Int,
            placeId: Long,
        ): CoursePlace =
            CoursePlace(
                id = 0L,
                order = order,
                placeId = placeId,
            )
    }
}

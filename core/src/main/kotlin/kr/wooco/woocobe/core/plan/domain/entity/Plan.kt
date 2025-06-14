package kr.wooco.woocobe.core.plan.domain.entity

import kr.wooco.woocobe.core.plan.domain.exception.AlreadyDeletedPlanException
import kr.wooco.woocobe.core.plan.domain.exception.InvalidPlanWriterException
import kr.wooco.woocobe.core.plan.domain.exception.NotExistsPlanException
import kr.wooco.woocobe.core.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.core.plan.domain.vo.PlanRegion
import kr.wooco.woocobe.core.plan.domain.vo.PlanStatus
import java.time.LocalDate

data class Plan(
    val id: Long,
    val userId: Long,
    val title: String,
    val contents: String,
    val region: PlanRegion,
    val visitDate: LocalDate,
    val places: List<PlanPlace>,
    val status: PlanStatus,
) {
    fun update(
        userId: Long,
        title: String,
        contents: String,
        region: PlanRegion,
        visitDate: LocalDate,
        placeIds: List<Long>,
    ): Plan {
        when {
            this.userId != userId -> throw InvalidPlanWriterException
            this.status != PlanStatus.ACTIVE -> throw NotExistsPlanException
        }
        return copy(
            title = title,
            contents = contents,
            region = region,
            visitDate = visitDate,
            places = processPlanPlaceOrder(placeIds),
        )
    }

    fun delete(userId: Long): Plan {
        when {
            this.userId != userId -> throw InvalidPlanWriterException
            this.status != PlanStatus.ACTIVE -> throw AlreadyDeletedPlanException
        }
        return copy(status = PlanStatus.DELETED)
    }

    companion object {
        fun create(
            userId: Long,
            title: String,
            contents: String,
            region: PlanRegion,
            visitDate: LocalDate,
            placeIds: List<Long>,
        ): Plan =
            Plan(
                id = 0L,
                userId = userId,
                title = title,
                contents = contents,
                region = region,
                visitDate = visitDate,
                places = processPlanPlaceOrder(placeIds),
                status = PlanStatus.ACTIVE,
            )

        private fun processPlanPlaceOrder(placeIds: List<Long>): List<PlanPlace> =
            placeIds.mapIndexed { index: Int, placeId: Long ->
                PlanPlace(
                    order = index,
                    placeId = placeId,
                )
            }
    }
}

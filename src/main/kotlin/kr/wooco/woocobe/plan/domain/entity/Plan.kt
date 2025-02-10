package kr.wooco.woocobe.plan.domain.entity

import kr.wooco.woocobe.plan.domain.exception.InvalidPlanWriterException
import kr.wooco.woocobe.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.plan.domain.vo.PlanRegion
import java.time.LocalDate

class Plan(
    val id: Long,
    val userId: Long,
    var title: String,
    var contents: String,
    var region: PlanRegion,
    var visitDate: LocalDate,
    var places: List<PlanPlace>,
) {
    fun update(
        userId: Long,
        title: String,
        contents: String,
        region: PlanRegion,
        visitDate: LocalDate,
        placeIds: List<Long>,
    ) {
        validateWriter(userId)

        this.title = title
        this.contents = contents
        this.region = region
        this.visitDate = visitDate
        this.places = processPlanPlaceOrder(placeIds)
    }

    fun validateWriter(userId: Long) {
        if (this.userId != userId) throw InvalidPlanWriterException
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

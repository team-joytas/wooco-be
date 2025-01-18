package kr.wooco.woocobe.plan.domain.model

import kr.wooco.woocobe.plan.domain.exception.InvalidPlanWriterException
import java.time.LocalDate

class Plan(
    val id: Long,
    val userId: Long,
    var title: String,
    var contents: String,
    var region: PlanRegion,
    var visitDate: LocalDate,
    var places: List<PlanPlace>,
    var categories: List<PlanCategory>,
) {
    fun isWriterOrThrow(userId: Long) {
        if (this.userId != userId) throw InvalidPlanWriterException
    }

    fun update(
        title: String,
        contents: String,
        region: PlanRegion,
        visitDate: LocalDate,
        placeIds: List<Long>,
        categories: List<String>,
    ) = apply {
        this.title = title
        this.contents = contents
        this.region = region
        this.visitDate = visitDate
        this.places = processPlanPlaceOrder(placeIds)
        this.categories = categories.map { PlanCategory.from(it) }
    }

    companion object {
        fun register(
            userId: Long,
            title: String,
            contents: String,
            region: PlanRegion,
            visitDate: LocalDate,
            categories: List<String>,
            placeIds: List<Long>,
        ): Plan =
            Plan(
                id = 0L,
                userId = userId,
                title = title,
                contents = contents,
                region = region,
                visitDate = visitDate,
                categories = categories.map { PlanCategory.from(it) },
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

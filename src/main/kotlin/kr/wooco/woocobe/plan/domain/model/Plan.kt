package kr.wooco.woocobe.plan.domain.model

import java.time.LocalDate

class Plan(
    val id: Long,
    val userId: Long,
    var title: String,
    var description: String,
    var region: PlanRegion,
    var visitDate: LocalDate,
    var places: List<PlanPlace>,
    var categories: List<PlanCategory>,
) {
    fun isWriterOrThrow(targetId: Long) {
        if (userId != targetId) {
            throw RuntimeException()
        }
    }

    fun update(
        title: String,
        description: String,
        region: PlanRegion,
        visitDate: LocalDate,
        placeIds: List<Long>,
        categories: List<String>,
    ) = apply {
        this.title = title
        this.description = description
        this.region = region
        this.visitDate = visitDate
        this.places = processPlanPlaceOrder(placeIds)
        this.categories = categories.map { PlanCategory.from(it) }
    }

    companion object {
        fun register(
            userId: Long,
            title: String,
            description: String,
            region: PlanRegion,
            visitDate: LocalDate,
            categories: List<String>,
            placeIds: List<Long>,
        ): Plan =
            Plan(
                id = 0L,
                userId = userId,
                title = title,
                description = description,
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

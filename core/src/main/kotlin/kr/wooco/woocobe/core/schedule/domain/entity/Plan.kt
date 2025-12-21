package kr.wooco.woocobe.core.schedule.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.schedule.domain.command.CreatePlanCommand
import kr.wooco.woocobe.core.schedule.domain.command.UpdatePlanInfoCommand
import kr.wooco.woocobe.core.schedule.domain.exception.PlanAlreadyDeletedException
import java.time.LocalDate

data class Plan(
    override val id: Long,
    val groupId: Long,
    val title: Title,
    val visitDate: VisitDate,
    val places: List<PlanPlace>,
    val status: Status,
): AggregateRoot() {

    @JvmInline
    value class Title(
        val value: String
    ) {
        init {
            require(value.length in 2 .. 10) { "제목은 2자 이상 10자 이하만 가능합니다." }
        }
    }

    @JvmInline
    value class VisitDate(
        val value: LocalDate
    ) {
        init {
            require(!value.isBefore(LocalDate.now())) { "방문 날짜는 금일 날짜 이후여야 합니다.." }
        }
    }

    enum class Status { ACTIVE, DELETED }

    fun updateInfo(command: UpdatePlanInfoCommand): Plan {
        requireActive()
        return copy(
            title = command.title,
            visitDate = command.visitDate,
            places = updatePlaces(command.placeIds),
        )
    }

    fun delete(): Plan {
        requireActive()
        return copy(
            status = Status.DELETED,
        )
    }

    fun isNew(): Boolean = id == 0L

    private fun requireActive() {
        if (status != Status.ACTIVE) throw PlanAlreadyDeletedException
    }

    private fun updatePlaces(placeIds: List<Long>): List<PlanPlace> {
        val places = this.places.associateBy { it.placeId }

        return placeIds.mapIndexed { index, placeId ->
            places[placeId]?.replaceOrder(index + 1)
                ?: PlanPlace.create(order = index + 1, placeId = placeId)
        }
    }

    companion object {
        fun create(
            command: CreatePlanCommand,
            identifier: (Plan) -> Long,
        ): Plan =
            Plan(
                id = 0L,
                groupId = command.groupId,
                title = command.title,
                visitDate = command.visitDate,
                places = orderingPlaces(command.placeIds),
                status = Status.ACTIVE,
            ).let {
                it.copy(id = identifier.invoke(it))
            }

        private fun orderingPlaces(placeIds: List<Long>): List<PlanPlace> =
            placeIds.mapIndexed { index: Int, placeId: Long ->
                PlanPlace.create(
                    order = index + 1,
                    placeId = placeId,
                )
            }
    }
}

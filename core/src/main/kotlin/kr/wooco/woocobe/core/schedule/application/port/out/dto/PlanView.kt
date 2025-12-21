package kr.wooco.woocobe.core.schedule.application.port.out.dto

import java.time.LocalDate

/**
 * 플랜 Read Model
 *
 * @author junseoparkk
 */
data class PlanView(
    val id: Long,
    val groupId: Long,
    val title: String,
    val visitDate: LocalDate,
    val places: List<PlanPlaceView>,
) {
    data class PlanPlaceView(
        val order: Int,
        val placeId: Long,
    )
}

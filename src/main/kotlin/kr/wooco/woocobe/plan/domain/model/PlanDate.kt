package kr.wooco.woocobe.plan.domain.model

import java.time.LocalDate

class PlanDate(
    val date: LocalDate,
) {
    companion object {
        fun register(date: LocalDate): PlanDate =
            require(!date.isBefore(LocalDate.now()))
                .run { PlanDate(date) }
    }
}

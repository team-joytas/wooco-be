package kr.wooco.woocobe.core.plan.application.scheduler

import kr.wooco.woocobe.core.plan.application.port.out.PlanQueryPort
import kr.wooco.woocobe.core.plan.domain.event.PlanShareRequestEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class PlanScheduler(
    private val planQueryPort: PlanQueryPort,
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun publishEventsForYesterdayPlans() {
        val yesterdayStart = LocalDate.now().minusDays(1).atStartOfDay()
        val yesterdayEnd = LocalDate.now().atStartOfDay()
        val plans = planQueryPort.getAllByCreatedAtBetweenWithActive(
            startDate = yesterdayStart,
            endDate = yesterdayEnd,
        )

        plans.forEach {
            val event = PlanShareRequestEvent(
                userId = it.userId,
                planId = it.id,
                planTitle = it.title,
            )
            eventPublisher.publishEvent(event)
        }
    }
}

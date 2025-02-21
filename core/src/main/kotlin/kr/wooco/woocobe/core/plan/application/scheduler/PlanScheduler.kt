package kr.wooco.woocobe.core.plan.application.scheduler

import kr.wooco.woocobe.core.plan.application.port.out.LoadPlanPersistencePort
import kr.wooco.woocobe.core.plan.domain.event.PlanShareRequestEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PlanScheduler(
    private val loadPlanPersistencePort: LoadPlanPersistencePort,
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    @Scheduled(cron = "0 0 * * * ?")
    fun checkUnsharedPlan() {
        loadPlanPersistencePort
            .getAllByIsSharedFalse()
            .map {
                PlanShareRequestEvent(
                    userId = it.userId,
                    planId = it.id,
                    planTitle = it.title,
                )
            }.forEach(eventPublisher::publishEvent)
    }
}

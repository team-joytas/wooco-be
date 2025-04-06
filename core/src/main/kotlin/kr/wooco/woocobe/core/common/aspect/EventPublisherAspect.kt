package kr.wooco.woocobe.core.common.aspect

import kr.wooco.woocobe.common.event.EventContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Aspect
@Component
class EventPublisherAspect(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Pointcut("within(kr.wooco.woocobe.core..*)")
    fun withinCoreModule() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    fun hasServiceAnnotation() {
    }

    /**
     * 도메인 이벤트가 등록된 후 영속화 레이어에서 영속화 메서드를 명시적으로 호출하지 않아
     * 발행되지 못한 잔여 이벤트를 발행합니다.
     *
     * 또한, 이 AOP 는 [kr.wooco.woocobe.core] 모듈 내의
     * [org.springframework.stereotype.Service] 어노테이션이 있는 클래스에만 적용되며,
     * AOP 실행 전 이전 요청 또는 중첩 호출 등에서 인해 남아 있을 수도 있는 잔여 이벤트를 초기화합니다.
     *
     * 따라서, 도메인 핸들링 후 영속화 레이어 내에서 명시적으로 영속화 메서드 `save()` 호출을 권장합니다.
     *
     * **NOTE** : 서비스 로직 수행중 예외가 발생시, 등록된 이벤트들은 발행되지 않으며 초기화됩니다.
     *
     * @see kr.wooco.woocobe.common.event.EventContext
     * @see kr.wooco.woocobe.mysql.common.advice.EventPublishingMethodInterceptor
     * @author JiHongKim98
     */
    @Around("withinCoreModule() && hasServiceAnnotation()")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        EventContext.clearEvents()

        try {
            val result = joinPoint.proceed()
            EventContext.raiseEvents { event ->
                applicationEventPublisher.publishEvent(event)
            }
            return result
        } catch (e: Throwable) {
            EventContext.clearEvents()
            throw e
        }
    }
}

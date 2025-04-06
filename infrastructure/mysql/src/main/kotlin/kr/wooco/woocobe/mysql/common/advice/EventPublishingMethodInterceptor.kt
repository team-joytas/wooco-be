package kr.wooco.woocobe.mysql.common.advice

import kr.wooco.woocobe.common.event.EventContext
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.context.ApplicationEventPublisher
import java.lang.reflect.Method

/**
 * Spring Data JPA의 영속화 메서드 호출 이후, 도메인 엔티티내에서 등록된 이벤트를
 * [EventContext]에서 가져와 실제로 이벤트를 발행시키는 메서드 인터셉터입니다.
 *
 * Spring data의 [org.springframework.data.domain.AbstractAggregateRoot]를 사용한다면,
 * 삭제 관련 메서드에서도 이벤트를 발행하지만, 우리 프로젝트에서는 `save()` 관련 메서드에서만 이벤트를 발행합니다.
 *
 * 참고 : [org.springframework.data.repository.core.support.EventPublishingRepositoryProxyPostProcessor]
 *
 * @see kr.wooco.woocobe.common.event.EventContext
 * @author JiHongKim98
 */
class EventPublishingMethodInterceptor(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        val result = invocation.proceed()
        if (isEventPublishingMethod(invocation.method)) {
            EventContext.raiseEvents { event ->
                applicationEventPublisher.publishEvent(event)
            }
        }
        return result
    }

    private fun isEventPublishingMethod(method: Method): Boolean {
        val methodName = method.name
        return methodName.startsWith(SAVE_METHOD_PREFIX)
    }

    companion object {
        private const val SAVE_METHOD_PREFIX = "save"
    }
}

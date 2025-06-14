package kr.wooco.woocobe.core.common.domain.entity

import kr.wooco.woocobe.common.event.EventContext
import kr.wooco.woocobe.core.common.domain.event.DomainEvent

abstract class AggregateRoot : DomainEntity() {
    /**
     * [EventContext]의 이벤트 저장소에 새로운 도메인 이벤트를 등록합니다.
     *
     * **Note** : [registerEvent] 메서드는 이벤트를 발행하지 않고, [EventContext]의 이벤트 저장소에 이벤트 등록만 진행합니다.
     *
     * @see kr.wooco.woocobe.common.event.EventContext
     * @param domainEvent [DomainEvent]
     * @author JiHongKim98
     */
    protected fun registerEvent(domainEvent: DomainEvent) {
        EventContext.appendEvent(domainEvent)
    }
}

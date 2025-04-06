package kr.wooco.woocobe.core.common.domain.event

import kr.wooco.woocobe.common.event.Event

abstract class DomainEvent : Event() {
    abstract val aggregateId: Long
}

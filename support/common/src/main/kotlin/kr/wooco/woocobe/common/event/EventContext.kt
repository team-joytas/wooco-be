package kr.wooco.woocobe.common.event

import io.github.oshai.kotlinlogging.KotlinLogging

private val log = KotlinLogging.logger {}

object EventContext {
    private val eventStore: ThreadLocal<MutableList<Event>> = ThreadLocal.withInitial { mutableListOf() }

    /**
     * 현재 ThreadLocal 내 이벤트를 저장합니다.
     *
     * @param event [Event]
     * @author JiHongKim98
     */
    fun appendEvent(event: Event) = eventStore.get().add(event)

    /**
     * 현재 ThreadLocal 내 저장된 이벤트들을 순차적으로 발행 후 이벤트 저장소를 정리합니다.
     *
     * @param publisher 각 이벤트를 발행하기 위한 함수.
     * @author JiHongKim98
     */
    fun raiseEvents(publisher: (Event) -> Unit) {
        eventStore.get().forEach { event ->
            publisher(event)
        }
        eventStore.remove()
    }

    /**
     * 현재 ThreadLocal 내 이벤트 저장소를 정리합니다.
     *
     * @author JiHongKim98
     */
    fun clearEvents() {
        if (log.isDebugEnabled()) {
            tracePublishFailedEvents()
        }
        eventStore.remove()
    }

    private fun tracePublishFailedEvents() {
        if (eventStore.get().isNotEmpty()) {
            log.error { "${eventStore.get().size}개의 잔여 이벤트가 남았지만, 해당 이벤트가 발행되지 못하고 삭제 되었습니다." }
            eventStore.get().forEach { event ->
                log.error { "발행되지 못한 이벤트 (class :: ${event.javaClass.simpleName}) | (eventId :: ${event.eventId})" }
            }
        }
    }
}

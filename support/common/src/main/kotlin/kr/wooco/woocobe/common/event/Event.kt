package kr.wooco.woocobe.common.event

import java.util.UUID

abstract class Event {
    val eventId: String = UUID.randomUUID().toString()
}

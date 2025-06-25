package kr.wooco.woocobe.core.place.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.place.domain.entity.Place

data class PlaceCreateEvent(
    override val aggregateId: Long,
    val placeId: Long,
    val name: String,
    val address: String,
) : DomainEvent() {
    companion object {
        fun from(place: Place): PlaceCreateEvent =
            PlaceCreateEvent(
                aggregateId = place.id,
                placeId = place.id,
                name = place.name,
                address = place.address,
            )
    }
}

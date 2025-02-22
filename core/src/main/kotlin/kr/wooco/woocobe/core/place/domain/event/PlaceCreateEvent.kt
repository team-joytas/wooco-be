package kr.wooco.woocobe.core.place.domain.event

import kr.wooco.woocobe.core.place.domain.entity.Place

data class PlaceCreateEvent(
    val placeId: Long,
    val name: String,
    val address: String,
) {
    companion object {
        fun from(place: Place): PlaceCreateEvent =
            PlaceCreateEvent(
                placeId = place.id,
                name = place.name,
                address = place.address,
            )
    }
}

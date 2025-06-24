package kr.wooco.woocobe.core.place.application.port.out

import kr.wooco.woocobe.core.place.domain.entity.Place

interface PlaceCommandPort {
    fun savePlace(place: Place): Long
}

package kr.wooco.woocobe.core.place.application.port.out

import kr.wooco.woocobe.core.place.domain.entity.Place

interface SavePlacePersistencePort {
    fun savePlace(place: Place): Place
}

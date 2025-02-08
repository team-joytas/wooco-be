package kr.wooco.woocobe.place.application.port.out

import kr.wooco.woocobe.place.domain.entity.Place

interface SavePlacePersistencePort {
    fun savePlace(place: Place): Place
}

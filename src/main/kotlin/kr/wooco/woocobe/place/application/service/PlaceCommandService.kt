package kr.wooco.woocobe.place.application.service

import kr.wooco.woocobe.place.application.port.`in`.CreatePlaceUseCase
import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.place.application.port.out.SavePlacePersistencePort
import kr.wooco.woocobe.place.domain.entity.Place
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceCommandService(
    private val savePlacePersistencePort: SavePlacePersistencePort,
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
) : CreatePlaceUseCase {
    @Transactional
    override fun createPlace(command: CreatePlaceUseCase.Command): Long {
        val existingPlace = loadPlacePersistencePort.getOrNullByKakaoMapPlaceId(command.kakaoPlaceId)

        if (existingPlace != null) {
            return existingPlace.id
        }

        val place = Place.create(
            name = command.name,
            kakaoPlaceId = command.kakaoPlaceId,
            address = command.address,
            latitude = command.latitude,
            longitude = command.longitude,
            phoneNumber = command.phoneNumber,
        )
        return savePlacePersistencePort.savePlace(place).id
    }
}

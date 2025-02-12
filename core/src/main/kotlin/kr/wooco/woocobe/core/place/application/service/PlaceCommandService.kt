package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceUseCase
import kr.wooco.woocobe.core.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.core.place.application.port.out.SavePlacePersistencePort
import kr.wooco.woocobe.core.place.domain.entity.Place
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceCommandService(
    private val savePlacePersistencePort: SavePlacePersistencePort,
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
) : CreatePlaceUseCase {
    @Transactional
    override fun createPlace(command: CreatePlaceUseCase.Command): Long {
        val place = loadPlacePersistencePort.getOrNullByKakaoMapPlaceId(command.kakaoPlaceId)
            ?: savePlacePersistencePort.savePlace(
                Place.create(
                    name = command.name,
                    kakaoPlaceId = command.kakaoPlaceId,
                    address = command.address,
                    latitude = command.latitude,
                    longitude = command.longitude,
                    phoneNumber = command.phoneNumber,
                ),
            )
        return place.id
    }
}

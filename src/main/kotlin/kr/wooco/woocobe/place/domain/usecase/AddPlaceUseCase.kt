package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddPlaceUseCaseInput(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
)

data class AddPlaceUseCaseOutput(
    val placeId: Long,
)

@Service
class AddPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<AddPlaceUseCaseInput, AddPlaceUseCaseOutput> {
    @Transactional
    override fun execute(input: AddPlaceUseCaseInput): AddPlaceUseCaseOutput {
        val existingPlace = placeStorageGateway.getByKakaoMapPlaceIdOrNull(input.kakaoMapPlaceId)

        if (existingPlace != null) {
            return AddPlaceUseCaseOutput(placeId = existingPlace.id)
        }

        val place = Place
            .register(
                name = input.name,
                latitude = input.latitude,
                longitude = input.longitude,
                address = input.address,
                kakaoMapPlaceId = input.kakaoMapPlaceId,
            )
        placeStorageGateway.save(place)

        return AddPlaceUseCaseOutput(placeId = place.id)
    }
}

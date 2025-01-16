package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddPlaceInput(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
)

data class AddPlaceOutput(
    val placeId: Long,
)

@Service
class AddPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<AddPlaceInput, AddPlaceOutput> {
    @Transactional
    override fun execute(input: AddPlaceInput): AddPlaceOutput {
        val existingPlace = placeStorageGateway.getOrNullByKakaoMapPlaceId(input.kakaoMapPlaceId)

        if (existingPlace != null) {
            return AddPlaceOutput(placeId = existingPlace.id)
        }

        val place = placeStorageGateway.save(
            Place.register(
                name = input.name,
                latitude = input.latitude,
                longitude = input.longitude,
                address = input.address,
                kakaoMapPlaceId = input.kakaoMapPlaceId,
            ),
        )
        return AddPlaceOutput(placeId = place.id)
    }
}

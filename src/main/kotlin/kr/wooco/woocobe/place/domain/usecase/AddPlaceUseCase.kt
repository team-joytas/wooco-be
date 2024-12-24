package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
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

@Service
class AddPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<AddPlaceUseCaseInput, Unit> {
    @Transactional
    override fun execute(input: AddPlaceUseCaseInput) {
        when {
            placeStorageGateway
                .existsByKakaoMapPlaceId(input.kakaoMapPlaceId)
                .not() -> throw RuntimeException()
        }

        Place
            .register(
                name = input.name,
                latitude = input.latitude,
                longitude = input.longitude,
                address = input.address,
                kakaoMapPlaceId = input.kakaoMapPlaceId,
            ).also(placeStorageGateway::save)
    }
}

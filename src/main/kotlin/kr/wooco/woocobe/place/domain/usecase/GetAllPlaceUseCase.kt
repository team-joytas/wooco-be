package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import org.springframework.stereotype.Service

data class GetAllPlaceInput(
    val placeIds: List<Long>,
)

data class GetAllPlaceOutput(
    val places: List<Place>,
)

@Service
class GetAllPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<GetAllPlaceInput, GetAllPlaceOutput> {
    override fun execute(input: GetAllPlaceInput): GetAllPlaceOutput {
        val places = placeStorageGateway.getAllByPlaceIds(input.placeIds)

        return GetAllPlaceOutput(
            places = places,
        )
    }
}

package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place

data class GetAllPlaceCaseInput(
    val placeIds: List<Long>,
)

data class GetAllPlaceCaseOutput(
    val places: List<Place>,
)

class GetAllPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<GetAllPlaceCaseInput, GetAllPlaceCaseOutput> {
    override fun execute(input: GetAllPlaceCaseInput): GetAllPlaceCaseOutput {
        val places = placeStorageGateway.getAllByPlaceIds(input.placeIds)

        return GetAllPlaceCaseOutput(
            places = places,
        )
    }
}

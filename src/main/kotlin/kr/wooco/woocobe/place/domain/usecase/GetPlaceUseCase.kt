package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import org.springframework.stereotype.Service

data class GetPlaceInput(
    val placeId: Long,
)

data class GetPlaceOutPut(
    val place: Place,
)

@Service
class GetPlaceUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<GetPlaceInput, GetPlaceOutPut> {
    override fun execute(input: GetPlaceInput): GetPlaceOutPut {
        val place = placeStorageGateway.getByPlaceId(input.placeId)
            ?: throw RuntimeException()

        return GetPlaceOutPut(
            place = place,
        )
    }
}

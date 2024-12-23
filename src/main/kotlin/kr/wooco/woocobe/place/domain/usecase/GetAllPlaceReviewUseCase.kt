package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import org.springframework.stereotype.Service

data class GetAllPlaceReviewInput(
    val placeId: Long,
)

data class GetAllPlaceReviewOutput(
    val placeReviews: List<PlaceReview>,
)

@Service
class GetAllPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<GetAllPlaceReviewInput, GetAllPlaceReviewOutput> {
    override fun execute(input: GetAllPlaceReviewInput): GetAllPlaceReviewOutput {
        val placeReviews = placeReviewStorageGateway.getAllByPlaceId(input.placeId)

        return GetAllPlaceReviewOutput(
            placeReviews = placeReviews,
        )
    }
}

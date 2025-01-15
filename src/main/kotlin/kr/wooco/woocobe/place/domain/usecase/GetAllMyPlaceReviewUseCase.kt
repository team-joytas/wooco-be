package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import org.springframework.stereotype.Service

data class GetUserAllPlaceReviewInput(
    val userId: Long,
)

data class GetUserAllPlaceReviewOutput(
    val placeReviews: List<PlaceReview>,
)

@Service
class GetAllMyPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<GetUserAllPlaceReviewInput, GetUserAllPlaceReviewOutput> {
    override fun execute(input: GetUserAllPlaceReviewInput): GetUserAllPlaceReviewOutput {
        val placeReviews = placeReviewStorageGateway.getAllByUserId(input.userId)

        return GetUserAllPlaceReviewOutput(
            placeReviews = placeReviews,
        )
    }
}

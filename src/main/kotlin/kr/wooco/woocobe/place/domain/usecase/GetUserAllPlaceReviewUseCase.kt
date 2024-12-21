package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import org.springframework.stereotype.Service

data class GetUserAllPlaceReviewInput(
    val userId: Long,
)

data class GetUserAllPlaceReviewOutput(
    val placeReviewList: List<PlaceReview>,
)

@Service
class GetUserAllPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<GetUserAllPlaceReviewInput, GetUserAllPlaceReviewOutput> {
    override fun execute(input: GetUserAllPlaceReviewInput): GetUserAllPlaceReviewOutput {
        val placeReviewList = placeReviewStorageGateway.getAllByUserId(input.userId)

        return GetUserAllPlaceReviewOutput(
            placeReviewList = placeReviewList,
        )
    }
}

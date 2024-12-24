package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import org.springframework.stereotype.Service

data class GetPlaceReviewInput(
    val placeReviewId: Long,
)

data class GetPlaceReviewOutput(
    val placeReview: PlaceReview,
)

@Service
class GetPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<GetPlaceReviewInput, GetPlaceReviewOutput> {
    override fun execute(input: GetPlaceReviewInput): GetPlaceReviewOutput {
        val placeReview = placeReviewStorageGateway.getByPlaceReviewId(input.placeReviewId)
            ?: throw RuntimeException()

        return GetPlaceReviewOutput(
            placeReview = placeReview,
        )
    }
}

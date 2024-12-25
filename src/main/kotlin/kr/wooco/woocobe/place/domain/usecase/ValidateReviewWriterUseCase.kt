package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway

data class ValidateReviewWriterInput(
    val userId: Long,
    val placeReviewId: Long,
)

class ValidateReviewWriterUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<ValidateReviewWriterInput, Unit> {
    override fun execute(input: ValidateReviewWriterInput) {
        val placeReview = placeReviewStorageGateway.getByPlaceReviewId(input.placeReviewId)
            ?: throw RuntimeException()

        when {
            placeReview.isWriter(input.userId).not() -> throw RuntimeException()
        }
    }
}

package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class DeletePlaceReviewInput(
    val userId: Long,
    val placeReviewId: Long,
)

@Service
class DeletePlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<DeletePlaceReviewInput, Unit> {
    @Transactional
    override fun execute(input: DeletePlaceReviewInput) {
        val placeReview = placeReviewStorageGateway
            .getByPlaceReviewId(input.placeReviewId)
            ?: throw RuntimeException()

        when {
            placeReview.isWriter(input.userId).not() -> throw RuntimeException()
        }

        placeReviewStorageGateway.deleteByPlaceReviewId(placeReviewId = placeReview.id)

        placeReview.place.decreaseReviewCount()
    }
}

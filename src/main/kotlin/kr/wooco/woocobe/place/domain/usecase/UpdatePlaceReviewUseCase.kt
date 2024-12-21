package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdatePlaceReviewInput(
    val userId: Long,
    val placeReviewId: Long,
    val rating: Double,
    var content: String,
    var oneLineReview: List<String>,
    var imageUrl: String,
)

@Service
class UpdatePlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
) : UseCase<UpdatePlaceReviewInput, Unit> {
    @Transactional
    override fun execute(input: UpdatePlaceReviewInput) {
        val placeReview = placeReviewStorageGateway.getByPlaceReviewId(input.placeReviewId)
            ?: throw RuntimeException()

        when {
            placeReview.isWriter(input.userId).not() -> throw RuntimeException()
        }

        placeReview
            .update(
                rating = input.rating,
                content = input.content,
                oneLinenReview = input.oneLineReview,
                imageUrl = input.imageUrl,
            ).also(placeReviewStorageGateway::save)
    }
}

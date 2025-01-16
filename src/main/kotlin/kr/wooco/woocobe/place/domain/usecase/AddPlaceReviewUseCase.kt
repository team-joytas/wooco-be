package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddPlaceReviewInput(
    val userId: Long,
    val placeId: Long,
    val rating: Double,
    val content: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
)

data class AddPlaceReviewOutput(
    val placeReviewId: Long,
)

// FIXME: 임시 장소 이미지 로직
@Service
class AddPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<AddPlaceReviewInput, AddPlaceReviewOutput> {
    @Transactional
    override fun execute(input: AddPlaceReviewInput): AddPlaceReviewOutput {
        val place = placeStorageGateway.getByPlaceId(input.placeId)

        val placeReview = placeReviewStorageGateway.save(
            PlaceReview.register(
                userId = input.userId,
                placeId = place.id,
                rating = input.rating,
                content = input.content,
                oneLineReview = input.oneLineReviews,
                imageUrls = input.imageUrls,
            ),
        )

        val mainImageUrl = placeReview.imageUrls[0]
        place.updateMainImageUrl(imageUrl = mainImageUrl)

        place.increaseReviewCounts()
        place.processPlaceStats(currentReviewRate = 0.0, reviewRate = input.rating)
        placeStorageGateway.save(place)

        return AddPlaceReviewOutput(
            placeReviewId = placeReview.id,
        )
    }
}

package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdatePlaceReviewInput(
    val userId: Long,
    val placeReviewId: Long,
    val rating: Double,
    var content: String,
    var oneLineReviews: List<String>,
    var imageUrls: List<String>,
)

@Service
class UpdatePlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<UpdatePlaceReviewInput, Unit> {
    @Transactional
    override fun execute(input: UpdatePlaceReviewInput) {
        val placeReview = placeReviewStorageGateway.getByPlaceReviewId(input.placeReviewId)
        placeReview.isValidWriter(input.userId)

        placeReview.update(
            rating = input.rating,
            content = input.content,
            oneLineReviews = input.oneLineReviews,
            imageUrls = input.imageUrls,
        )
        placeReviewStorageGateway.save(placeReview)

        val place = placeStorageGateway.getByPlaceId(placeReview.placeId)
        place.processPlaceStats(
            currentReviewRate = placeReview.rating,
            reviewRate = input.rating,
        )
        placeStorageGateway.save(place)
    }
}

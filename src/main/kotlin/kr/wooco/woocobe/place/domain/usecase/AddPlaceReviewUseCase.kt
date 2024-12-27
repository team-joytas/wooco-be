package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.user.domain.model.User
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

@Service
class AddPlaceReviewUseCase(
    private val placeReviewStorageGateway: PlaceReviewStorageGateway,
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<AddPlaceReviewInput, Unit> {
    @Transactional
    override fun execute(input: AddPlaceReviewInput) {
        val user = User.register(userId = input.userId)
        val place = placeStorageGateway.getByPlaceId(input.placeId)
            ?: throw RuntimeException()

        PlaceReview
            .register(
                user = user,
                place = place,
                rating = input.rating,
                content = input.content,
                oneLineReview = input.oneLineReviews,
                imageUrls = input.imageUrls,
            ).also(placeReviewStorageGateway::save)

        place.addReview(input.rating)
        placeStorageGateway.save(place)
    }
}

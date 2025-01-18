package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

data class PlaceReviewDetailsResponse(
    val id: Long,
    val writer: PlaceReviewWriterResponse,
    val rating: Double,
    val contents: String,
    val createdAt: LocalDateTime,
    val oneLineReviews: List<PlaceOneLineReviewResponse>,
    val imageUrls: List<String>,
) {
    companion object {
        fun of(
            placeReview: PlaceReview,
            user: User,
        ): PlaceReviewDetailsResponse =
            PlaceReviewDetailsResponse(
                id = placeReview.id,
                writer = PlaceReviewWriterResponse.from(user),
                rating = placeReview.rating,
                contents = placeReview.contents,
                createdAt = placeReview.writeDateTime,
                oneLineReviews = placeReview.oneLineReviews
                    .map { placeOneLineReview ->
                        PlaceOneLineReviewResponse.from(placeOneLineReview.contents)
                    },
                imageUrls = placeReview.imageUrls,
            )

        fun listOf(
            placeReviews: List<PlaceReview>,
            users: List<User>,
        ): List<PlaceReviewDetailsResponse> {
            val userMap = users.associateBy { it.id }

            return placeReviews.map { placeReview ->
                val writer = requireNotNull(userMap[placeReview.userId])

                PlaceReviewDetailsResponse(
                    id = placeReview.id,
                    writer = PlaceReviewWriterResponse.from(writer),
                    rating = placeReview.rating,
                    contents = placeReview.contents,
                    createdAt = placeReview.writeDateTime,
                    oneLineReviews = placeReview.oneLineReviews
                        .map { placeOneLineReview ->
                            PlaceOneLineReviewResponse.from(placeOneLineReview.contents)
                        },
                    imageUrls = placeReview.imageUrls,
                )
            }
        }
    }
}

data class PlaceOneLineReviewResponse(
    val contents: String,
) {
    companion object {
        fun from(oneLineReview: String): PlaceOneLineReviewResponse =
            PlaceOneLineReviewResponse(
                contents = oneLineReview,
            )
    }
}

data class PlaceReviewWriterResponse(
    val id: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(user: User): PlaceReviewWriterResponse =
            PlaceReviewWriterResponse(
                id = user.id,
                name = user.name,
                profileUrl = user.profileUrl,
            )
    }
}

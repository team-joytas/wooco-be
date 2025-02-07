package kr.wooco.woocobe.placereview.adapter.`in`.web.response

import kr.wooco.woocobe.placereview.application.port.`in`.result.PlaceReviewResult
import kr.wooco.woocobe.user.domain.entity.User
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
        fun from(placeReviewResult: PlaceReviewResult): PlaceReviewDetailsResponse =
            PlaceReviewDetailsResponse(
                id = placeReviewResult.placeReviewId,
                writer = PlaceReviewWriterResponse(
                    id = placeReviewResult.writerId,
                    name = placeReviewResult.writerName,
                    profileUrl = placeReviewResult.writerProfileUrl,
                ),
                rating = placeReviewResult.rating,
                contents = placeReviewResult.contents,
                createdAt = placeReviewResult.createdAt,
                oneLineReviews = placeReviewResult.oneLineReviews
                    .map { oneLineReview ->
                        PlaceOneLineReviewResponse.from(oneLineReview)
                    },
                imageUrls = placeReviewResult.reviewImageUrls,
            )

        fun listFrom(placeReviewResult: List<PlaceReviewResult>): List<PlaceReviewDetailsResponse> =
            placeReviewResult.map {
                PlaceReviewDetailsResponse(
                    id = it.placeReviewId,
                    writer = PlaceReviewWriterResponse(
                        id = it.writerId,
                        name = it.writerName,
                        profileUrl = it.writerProfileUrl,
                    ),
                    rating = it.rating,
                    contents = it.contents,
                    createdAt = it.createdAt,
                    oneLineReviews = it.oneLineReviews
                        .map { oneLineReview ->
                            PlaceOneLineReviewResponse.from(oneLineReview)
                        },
                    imageUrls = it.reviewImageUrls,
                )
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
                name = user.profile.name,
                profileUrl = user.profile.profileUrl,
            )
    }
}

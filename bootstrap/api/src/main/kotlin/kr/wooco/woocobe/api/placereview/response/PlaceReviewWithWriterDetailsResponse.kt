package kr.wooco.woocobe.api.placereview.response

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithWriterResult
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceReviewWithWriterDetailsResponse(
    val id: Long,
    val writer: PlaceReviewWriterResponse,
    val rating: Double,
    val contents: String,
    val createdAt: LocalDateTime,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    companion object {
        fun from(placeReviewWithWriterResult: PlaceReviewWithWriterResult): PlaceReviewWithWriterDetailsResponse =
            PlaceReviewWithWriterDetailsResponse(
                id = placeReviewWithWriterResult.placeReviewId,
                writer = PlaceReviewWriterResponse(
                    id = placeReviewWithWriterResult.writerId,
                    name = placeReviewWithWriterResult.writerName,
                    profileUrl = placeReviewWithWriterResult.writerProfileUrl,
                ),
                rating = placeReviewWithWriterResult.rating,
                contents = placeReviewWithWriterResult.contents,
                createdAt = placeReviewWithWriterResult.createdAt,
                oneLineReviews = placeReviewWithWriterResult.oneLineReviews.map { it },
                imageUrls = placeReviewWithWriterResult.reviewImageUrls,
            )

        fun listFrom(placeReviewWithWriterResults: List<PlaceReviewWithWriterResult>): List<PlaceReviewWithWriterDetailsResponse> =
            placeReviewWithWriterResults.map {
                PlaceReviewWithWriterDetailsResponse(
                    id = it.placeReviewId,
                    writer = PlaceReviewWriterResponse(
                        id = it.writerId,
                        name = it.writerName,
                        profileUrl = it.writerProfileUrl,
                    ),
                    rating = it.rating,
                    contents = it.contents,
                    createdAt = it.createdAt,
                    oneLineReviews = it.oneLineReviews.map { oneLineReview -> oneLineReview },
                    imageUrls = it.reviewImageUrls,
                )
            }
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

package kr.wooco.woocobe.api.placereview.response

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewResult
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceReviewDetailsResponse(
    val id: Long,
    val writer: PlaceReviewWriterResponse,
    val rating: Double,
    val contents: String,
    val createdAt: LocalDateTime,
    val oneLineReviews: List<String>,
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
                oneLineReviews = placeReviewResult.oneLineReviews.map { it.contents },
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
                    oneLineReviews = it.oneLineReviews.map { oneLineReview -> oneLineReview.contents },
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

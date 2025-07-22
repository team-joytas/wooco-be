package kr.wooco.woocobe.core.place.application.port.`in`.result

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceWithPlaceReviewsResult(
    val place: PlaceResult,
    val placeReviews: List<PlaceReviewResult>,
) {
    data class PlaceReviewResult(
        val placeReviewId: Long,
        val writer: PlaceReviewWriterResult,
        val contents: String,
        val rating: Double,
        val oneLineReviews: List<String>,
        val reviewImageUrls: List<String>,
        val createdAt: LocalDateTime,
    )

    data class PlaceReviewWriterResult(
        val id: Long,
        val name: String,
        val profileUrl: String,
    )

    companion object {
        fun of(
            place: Place,
            placeOneLineReviewsStats: List<PlaceOneLineReviewStat>,
            placeReviews: List<PlaceReview>,
            writers: List<User>,
        ): PlaceWithPlaceReviewsResult {
            val writerMap = writers.associateBy { it.id }
            return PlaceWithPlaceReviewsResult(
                place = PlaceResult.of(
                    place,
                    placeOneLineReviewsStats,
                ),
                placeReviews = placeReviews.map { placeReview ->
                    val writer = requireNotNull(writerMap[placeReview.userId])
                    PlaceReviewResult(
                        placeReviewId = placeReview.id,
                        writer = PlaceReviewWriterResult(
                            id = writer.id,
                            name = writer.profile.name,
                            profileUrl = writer.profile.profileUrl,
                        ),
                        contents = placeReview.contents.value,
                        rating = placeReview.rating.score,
                        oneLineReviews = placeReview.oneLineReviews.map { it.value },
                        reviewImageUrls = placeReview.imageUrls,
                        createdAt = placeReview.writeDateTime,
                    )
                },
            )
        }
    }
}

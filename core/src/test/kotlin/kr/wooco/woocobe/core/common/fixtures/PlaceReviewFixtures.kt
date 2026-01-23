package kr.wooco.woocobe.core.common.fixtures

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import java.time.LocalDateTime

object PlaceReviewFixtures {
    const val DEFAULT_USER_ID: Long = 100L
    const val DEFAULT_PLACE_ID: Long = 10L

    private val DEFAULT_WRITE_TIME: LocalDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0)

    fun activePlaceReview(
        id: Long = 1L,
        userId: Long = DEFAULT_USER_ID,
        placeId: Long = DEFAULT_PLACE_ID,
        writeDateTime: LocalDateTime = DEFAULT_WRITE_TIME,
        rating: PlaceReviewRating = PlaceReviewRating.FOUR,
        contents: String = "좋아요",
        oneLineReviews: List<String> = listOf("조용해요", "커피굿"),
        imageUrls: List<String> = listOf("https://img.wooco.com/1.png"),
    ): PlaceReview =
        PlaceReview(
            id = id,
            userId = userId,
            placeId = placeId,
            writeDateTime = writeDateTime,
            rating = rating,
            contents = PlaceReview.Contents(contents),
            oneLineReviews = oneLineReviews.map { PlaceReview.OneLineReview(it) },
            imageUrls = imageUrls,
            status = PlaceReview.Status.ACTIVE,
        )

    fun deletedPlaceReview(
        id: Long = 1L,
        userId: Long = DEFAULT_USER_ID,
        placeId: Long = DEFAULT_PLACE_ID,
    ): PlaceReview =
        PlaceReview(
            id = id,
            userId = userId,
            placeId = placeId,
            writeDateTime = DEFAULT_WRITE_TIME,
            rating = PlaceReviewRating.FOUR,
            contents = PlaceReview.Contents("삭제됨"),
            oneLineReviews = emptyList(),
            imageUrls = emptyList(),
            status = PlaceReview.Status.DELETED,
        )

    fun createCommand(
        userId: Long = DEFAULT_USER_ID,
        placeId: Long = DEFAULT_PLACE_ID,
        rating: Double = 4.0,
        contents: String = "분위기 좋아요",
        oneLineReviews: List<String> = listOf("조용해요", "조용해요", "커피굿"),
        imageUrls: List<String> = listOf("https://img.wooco.com/1.png", "https://img.wooco.com/2.png"),
    ): CreatePlaceReviewUseCase.Command =
        CreatePlaceReviewUseCase.Command(
            userId = userId,
            placeId = placeId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )

    fun updateCommand(
        placeReviewId: Long,
        userId: Long = DEFAULT_USER_ID,
        rating: Double = 4.0,
        contents: String = "수정했어요",
        oneLineReviews: List<String> = listOf("조용해요", "조용해요", "커피굿"),
        imageUrls: List<String> = listOf("https://img.wooco.com/new1.png"),
    ): UpdatePlaceReviewUseCase.Command =
        UpdatePlaceReviewUseCase.Command(
            placeReviewId = placeReviewId,
            userId = userId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )

    fun deleteCommand(
        placeReviewId: Long,
        userId: Long = DEFAULT_USER_ID,
    ): DeletePlaceReviewUseCase.Command =
        DeletePlaceReviewUseCase.Command(
            userId = userId,
            placeReviewId = placeReviewId,
        )
}

package kr.wooco.woocobe.core.placereview.domain.entity

import kr.wooco.woocobe.core.placereview.domain.exception.InvalidPlaceReviewWriterException
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import java.time.LocalDateTime

data class PlaceReview(
    val id: Long,
    val userId: Long,
    val placeId: Long,
    val writeDateTime: LocalDateTime,
    val rating: PlaceReviewRating,
    val contents: String,
    val imageUrls: List<String>,
) {
    init {
        require(imageUrls.size <= 10) { "이미지는 최대 10개까지 등록할 수 있습니다." }
    }

    fun update(
        userId: Long,
        rating: PlaceReviewRating,
        contents: String,
        imageUrls: List<String>,
    ): PlaceReview {
        isValidWriter(userId)
        return copy(
            rating = rating,
            contents = contents,
            imageUrls = imageUrls,
        )
    }

    fun isValidWriter(userId: Long) {
        if (this.userId != userId) {
            throw InvalidPlaceReviewWriterException
        }
    }

    companion object {
        fun create(
            userId: Long,
            placeId: Long,
            rating: PlaceReviewRating,
            contents: String,
            imageUrls: List<String>,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                userId = userId,
                placeId = placeId,
                writeDateTime = LocalDateTime.now(),
                rating = rating,
                contents = contents,
                imageUrls = imageUrls,
            )
    }
}

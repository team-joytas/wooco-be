package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

interface PlaceReviewQueryPort {
    fun getByPlaceReviewId(placeReviewId: Long): PlaceReview

    fun getAllByPlaceId(placeId: Long): List<PlaceReview>

    fun getAllByUserId(userId: Long): List<PlaceReview>

    fun getAverageRatingByPlaceId(placeId: Long): Double

    fun getPlaceReviewStatsByPlaceId(placeId: Long): PlaceReviewStats

    fun existsByPlaceIdAndUserId(
        placeId: Long,
        userId: Long,
    ): Boolean

    fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat>

    fun countByUserId(userId: Long): Long

    // 더 괜찮은 네이밍으로 수정 가능
    fun getTop2ByPlaceId(placeId: Long): List<PlaceReview>
}

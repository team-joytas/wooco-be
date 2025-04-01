package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
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

    fun getAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReview>

    fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceOneLineReview>

    fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat>

    fun countByUserId(userId: Long): Long
}

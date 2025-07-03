package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.placereview.application.port.`in`.ExistsPlaceReviewWriterUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllUserPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithPlaceResult
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithWriterResult
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceReviewQueryService(
    private val userQueryPort: UserQueryPort,
    private val placeReviewQueryPort: PlaceReviewQueryPort,
    private val placeQueryPort: PlaceQueryPort,
) : ReadAllPlaceReviewUseCase,
    ReadAllUserPlaceReviewUseCase,
    ReadPlaceReviewUseCase,
    ExistsPlaceReviewWriterUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlaceReview(query: ReadAllPlaceReviewUseCase.Query): List<PlaceReviewWithWriterResult> {
        val placeReviews = placeReviewQueryPort.getAllByPlaceId(query.placeId)
        val writerIds = placeReviews.map { it.userId }.distinct()
        val writers = userQueryPort.getAllByUserIds(writerIds)
        return PlaceReviewWithWriterResult.listOf(placeReviews, writers)
    }

    @Transactional(readOnly = true)
    override fun readAllUserPlaceReview(query: ReadAllUserPlaceReviewUseCase.Query): List<PlaceReviewWithPlaceResult> {
        val placeReviews = placeReviewQueryPort.getAllByUserId(query.userId)
        val placeIds = placeReviews.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        return PlaceReviewWithPlaceResult.listOf(placeReviews, places)
    }

    @Transactional(readOnly = true)
    override fun readPlaceReview(query: ReadPlaceReviewUseCase.Query): PlaceReviewWithWriterResult {
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(query.placeReviewId)
        val writer = userQueryPort.getByUserId(placeReview.userId)
        return PlaceReviewWithWriterResult.of(placeReview, writer)
    }

    override fun existsPlaceReviewWriter(query: ExistsPlaceReviewWriterUseCase.Query): Boolean =
        placeReviewQueryPort.existsByPlaceIdAndUserId(query.placeId, query.userId)
}

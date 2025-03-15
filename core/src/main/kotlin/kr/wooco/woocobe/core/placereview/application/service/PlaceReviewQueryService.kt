package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.placereview.application.port.`in`.ExistsPlaceReviewWriterUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllUserPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithPlaceResult
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithWriterResult
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.core.user.application.port.out.LoadUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceReviewQueryService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
    private val loadPlaceReviewPersistencePort: LoadPlaceReviewPersistencePort,
    private val loadPlaceOneLineReviewPersistencePort: LoadPlaceOneLineReviewPersistencePort,
    private val placeQueryPort: PlaceQueryPort,
) : ReadAllPlaceReviewUseCase,
    ReadAllUserPlaceReviewUseCase,
    ReadPlaceReviewUseCase,
    ExistsPlaceReviewWriterUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlaceReview(query: ReadAllPlaceReviewUseCase.Query): List<PlaceReviewWithWriterResult> {
        val placeReviews = loadPlaceReviewPersistencePort.getAllByPlaceId(query.placeId)
        val placeOneLineReviews =
            loadPlaceOneLineReviewPersistencePort.getAllByPlaceReviewIds(placeReviews.map { it.id })
        val writerIds = placeReviews.map { it.userId }.distinct()
        val writers = loadUserPersistencePort.getAllByUserIds(writerIds)
        return PlaceReviewWithWriterResult.listOf(placeReviews, placeOneLineReviews, writers)
    }

    @Transactional(readOnly = true)
    override fun readAllUserPlaceReview(query: ReadAllUserPlaceReviewUseCase.Query): List<PlaceReviewWithPlaceResult> {
        val placeReviews = loadPlaceReviewPersistencePort.getAllByUserId(query.userId)
        val placeOneLineReviews =
            loadPlaceOneLineReviewPersistencePort.getAllByPlaceReviewIds(placeReviews.map { it.id })
        val placeIds = placeReviews.map { it.placeId }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds)
        return PlaceReviewWithPlaceResult.listOf(placeReviews, placeOneLineReviews, places)
    }

    @Transactional(readOnly = true)
    override fun readPlaceReview(query: ReadPlaceReviewUseCase.Query): PlaceReviewWithWriterResult {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(query.placeReviewId)
        val placeOneLineReviews = loadPlaceOneLineReviewPersistencePort.getAllByPlaceReviewId(placeReview.id)
        val writer = loadUserPersistencePort.getByUserId(placeReview.userId)
        return PlaceReviewWithWriterResult.of(placeReview, placeOneLineReviews, writer)
    }

    override fun existsPlaceReviewWriter(query: ExistsPlaceReviewWriterUseCase.Query): Boolean =
        loadPlaceReviewPersistencePort.existsByPlaceIdAndUserId(query.placeId, query.userId)
}

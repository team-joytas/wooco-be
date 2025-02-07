package kr.wooco.woocobe.placereview.application.service

import kr.wooco.woocobe.placereview.application.port.`in`.ReadAllMyPlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.`in`.ReadAllPlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.`in`.ReadPlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.`in`.result.PlaceReviewResult
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceReviewQueryService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
    private val loadPlaceReviewPersistencePort: LoadPlaceReviewPersistencePort,
) : ReadAllPlaceReviewUseCase,
    ReadAllMyPlaceReviewUseCase,
    ReadPlaceReviewUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlaceReview(query: ReadAllPlaceReviewUseCase.Query): List<PlaceReviewResult> {
        val placeReviews = loadPlaceReviewPersistencePort.getAllByPlaceId(query.placeId)
        val writerIds = placeReviews.map { it.userId }.distinct()
        val writers = loadUserPersistencePort.getAllByUserIds(writerIds)

        return PlaceReviewResult.listOf(placeReviews, writers)
    }

    @Transactional(readOnly = true)
    override fun readAllMyPlaceReview(query: ReadAllMyPlaceReviewUseCase.Query): List<PlaceReviewResult> {
        val placeReviews = loadPlaceReviewPersistencePort.getAllByUserId(query.userId)
        val writer = loadUserPersistencePort.getByUserId(query.userId)

        return PlaceReviewResult.listOf(placeReviews, listOf(writer))
    }

    @Transactional(readOnly = true)
    override fun readPlaceReview(query: ReadPlaceReviewUseCase.Query): PlaceReviewResult {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(query.placeReviewId)
        val writer = loadUserPersistencePort.getByUserId(placeReview.userId)

        return PlaceReviewResult.of(placeReview, writer)
    }
}

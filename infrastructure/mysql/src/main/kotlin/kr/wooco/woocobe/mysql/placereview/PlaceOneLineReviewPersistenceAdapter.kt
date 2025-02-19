package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.application.port.out.DeletePlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.SaveAllPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.mysql.placereview.repository.PlaceOneLineReviewJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
internal class PlaceOneLineReviewPersistenceAdapter(
    private val placeOneLineReviewJpaRepository: PlaceOneLineReviewJpaRepository,
    private val placeOneLineReviewPersistenceMapper: PlaceOneLineReviewPersistenceMapper,
) : LoadPlaceOneLineReviewPersistencePort,
    DeletePlaceOneLineReviewPersistencePort,
    SaveAllPlaceOneLineReviewPersistencePort {
    override fun saveAllPlaceOneLineReview(placeOneLineReview: List<PlaceOneLineReview>): List<PlaceOneLineReview> {
        val placeOneLineReviewStatEntities =
            placeOneLineReview.map(placeOneLineReviewPersistenceMapper::toEntity)
        val savedEntities = placeOneLineReviewJpaRepository.saveAll(placeOneLineReviewStatEntities)
        return savedEntities.map(placeOneLineReviewPersistenceMapper::toDomain)
    }

    override fun getAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReview> {
        val placeOneLineReviewEntities = placeOneLineReviewJpaRepository.findAllByPlaceReviewId(placeReviewId)
        return placeOneLineReviewEntities.map(placeOneLineReviewPersistenceMapper::toDomain)
    }

    override fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceOneLineReview> {
        val placeOneLineReviewEntities =
            placeOneLineReviewJpaRepository.findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewIds)
        return placeOneLineReviewEntities.map(placeOneLineReviewPersistenceMapper::toDomain)
    }

    override fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat> =
        placeOneLineReviewJpaRepository.findPlaceOneLineReviewStatsByPlaceId(placeId, PageRequest.of(0, 5))

    override fun deleteAllByPlaceReviewId(placeReviewId: Long) {
        placeOneLineReviewJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }
}

package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.exception.NotExistsPlaceReviewException
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewJpaEntity
import kr.wooco.woocobe.mysql.placereview.repository.PlaceOneLineReviewJpaRepository
import kr.wooco.woocobe.mysql.placereview.repository.PlaceReviewImageJpaRepository
import kr.wooco.woocobe.mysql.placereview.repository.PlaceReviewJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class PlaceReviewPersistenceAdapter(
    private val placeReviewJpaRepository: PlaceReviewJpaRepository,
    private val placeReviewImageJpaRepository: PlaceReviewImageJpaRepository,
    private val placeOneLineReviewJpaRepository: PlaceOneLineReviewJpaRepository,
) : PlaceReviewCommandPort,
    PlaceReviewQueryPort {
    // TODO: savePlaceReview 최적화 필요
    @Transactional
    override fun savePlaceReview(placeReview: PlaceReview): Long {
        val placeReviewJpaEntity = placeReviewJpaRepository.save(PlaceReviewPersistenceMapper.toJpaEntity(placeReview))

        placeOneLineReviewJpaRepository.deleteAllByPlaceReviewId(placeReviewJpaEntity.id)
        val placeOneLineReviewJpaEntities = PlaceOneLineReviewJpaEntity.listOf(placeReview, placeReviewJpaEntity)
        placeOneLineReviewJpaRepository.saveAll(placeOneLineReviewJpaEntities)

        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewJpaEntity.id)
        val placeReviewImageJpaEntities = PlaceReviewImageJpaEntity.listOf(placeReview, placeReviewJpaEntity)
        placeReviewImageJpaRepository.saveAll(placeReviewImageJpaEntities)
        return placeReviewJpaEntity.id
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview {
        val placeReviewJpaEntity = placeReviewJpaRepository.findByIdOrNull(placeReviewId)
            ?: throw NotExistsPlaceReviewException
        val placeOneLineReviewJpaEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewId(placeReviewJpaEntity.id)
        val placeReviewImageJpaEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewId(placeReviewJpaEntity.id)
        return PlaceReviewPersistenceMapper.toDomainEntity(
            placeReviewJpaEntity,
            placeOneLineReviewJpaEntities,
            placeReviewImageJpaEntities,
        )
    }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewJpaEntities = placeReviewJpaRepository.findAllByPlaceId(placeId)
        return convertPlaceReviews(placeReviewJpaEntities)
    }

    override fun getAllByUserId(userId: Long): List<PlaceReview> {
        val placeReviewJpaEntities = placeReviewJpaRepository.findAllByUserId(userId)
        return convertPlaceReviews(placeReviewJpaEntities)
    }

    private fun convertPlaceReviews(placeReviewJpaEntities: List<PlaceReviewJpaEntity>): List<PlaceReview> {
        val placeReviewIds = placeReviewJpaEntities.map { it.id }
        val placeOneLineReviewJpaEntities = placeOneLineReviewJpaRepository.findAllByPlaceReviewIdIn(placeReviewIds)
        val placeReviewImageJpaEntities = placeReviewImageJpaRepository.findAllByPlaceReviewIdIn(placeReviewIds)

        return placeReviewJpaEntities.map { placeReviewJpaEntity ->
            PlaceReviewPersistenceMapper.toDomainEntity(
                placeReviewJpaEntity = placeReviewJpaEntity,
                placeOneLineReviewJpaEntities = placeOneLineReviewJpaEntities.filter { it.placeReviewId == placeReviewJpaEntity.id },
                placeReviewImageJpaEntities = placeReviewImageJpaEntities.filter { it.placeReviewId == placeReviewJpaEntity.id },
            )
        }
    }

    override fun getAverageRatingByPlaceId(placeId: Long): Double = placeReviewJpaRepository.findAverageRatingByPlaceId(placeId)

    override fun getPlaceReviewStatsByPlaceId(placeId: Long): PlaceReviewStats =
        placeReviewJpaRepository.findPlaceReviewStatsByPlaceId(placeId)

    override fun existsByPlaceIdAndUserId(
        placeId: Long,
        userId: Long,
    ): Boolean = placeReviewJpaRepository.existsByPlaceIdAndUserId(placeId, userId)

    override fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat> =
        placeOneLineReviewJpaRepository.findPlaceOneLineReviewStatsByPlaceId(placeId, PageRequest.of(0, 5))

    override fun countByUserId(userId: Long): Long = placeReviewJpaRepository.countByUserId(userId)

    override fun getRecent2ByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewJpaEntities = placeReviewJpaRepository.findRecent2ByPlaceId(placeId, PageRequest.of(0, 2))
        return convertPlaceReviews(placeReviewJpaEntities)
    }
}

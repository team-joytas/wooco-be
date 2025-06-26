package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.exception.NotExistsPlaceReviewException
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
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
    @Transactional
    override fun savePlaceReview(placeReview: PlaceReview): Long {
        val placeReviewJpaEntity = placeReviewJpaRepository.save(PlaceReviewPersistenceMapper.toJpaEntity(placeReview))

        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewJpaEntity.id)
        val placeReviewImageJpaEntities = placeReview.imageUrls.map {
            PlaceReviewImageJpaEntity(
                placeReviewId = placeReviewJpaEntity.id,
                imageUrl = it,
            )
        }
        placeReviewImageJpaRepository.saveAll(placeReviewImageJpaEntities)
        return placeReviewJpaEntity.id
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview {
        val placeReviewEntity = placeReviewJpaRepository.findByIdOrNull(placeReviewId)
            ?: throw NotExistsPlaceReviewException
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewId(placeReviewEntity.id)
        return PlaceReviewPersistenceMapper.toDomainEntity(
            placeReviewEntity,
            placeReviewImageEntities,
        )
    }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByPlaceIdOrderByCreatedAt(placeId)
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })
        return placeReviewEntities.map { placeReviewEntity ->
            PlaceReviewPersistenceMapper.toDomainEntity(
                placeReviewJpaEntity = placeReviewEntity,
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun getAllByUserId(userId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByUserIdOrderByCreatedAt(userId)
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })
        return placeReviewEntities.map { placeReviewEntity ->
            PlaceReviewPersistenceMapper.toDomainEntity(
                placeReviewJpaEntity = placeReviewEntity,
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
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

    override fun deletePlaceReviewId(placeReviewId: Long) {
        placeReviewJpaRepository.deleteById(placeReviewId)
        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }

    override fun saveAllPlaceOneLineReview(placeOneLineReview: List<PlaceOneLineReview>): List<PlaceOneLineReview> {
        val placeOneLineReviewStatEntities =
            placeOneLineReview.map(PlaceOneLineReviewPersistenceMapper::toJpaEntity)
        val savedEntities = placeOneLineReviewJpaRepository.saveAll(placeOneLineReviewStatEntities)
        return savedEntities.map(PlaceOneLineReviewPersistenceMapper::toDomainEntity)
    }

    override fun getAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReview> {
        val placeOneLineReviewEntities = placeOneLineReviewJpaRepository.findAllByPlaceReviewId(placeReviewId)
        return placeOneLineReviewEntities.map(PlaceOneLineReviewPersistenceMapper::toDomainEntity)
    }

    override fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceOneLineReview> {
        val placeOneLineReviewEntities =
            placeOneLineReviewJpaRepository.findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewIds)
        return placeOneLineReviewEntities.map(PlaceOneLineReviewPersistenceMapper::toDomainEntity)
    }

    override fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat> =
        placeOneLineReviewJpaRepository.findPlaceOneLineReviewStatsByPlaceId(placeId, PageRequest.of(0, 5))

    override fun countByUserId(userId: Long): Long = placeReviewJpaRepository.countByUserId(userId)

    override fun deleteAllByPlaceReviewId(placeReviewId: Long) {
        placeOneLineReviewJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }
}

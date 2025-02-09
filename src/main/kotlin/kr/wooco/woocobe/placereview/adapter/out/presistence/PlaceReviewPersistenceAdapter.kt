package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.placereview.adapter.out.presistence.repository.PlaceOneLineReviewJpaRepository
import kr.wooco.woocobe.placereview.adapter.out.presistence.repository.PlaceOneLineReviewStatJpaRepository
import kr.wooco.woocobe.placereview.adapter.out.presistence.repository.PlaceReviewImageJpaRepository
import kr.wooco.woocobe.placereview.adapter.out.presistence.repository.PlaceReviewJpaRepository
import kr.wooco.woocobe.placereview.application.port.out.DeletePlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceOneLineReviewStatPersistencePort
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.application.port.out.SavePlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.domain.entity.PlaceOneLineReviewStat
import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.placereview.domain.exception.NotExistsPlaceReviewException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

// TODO: 한줄평 통계 저장 기능 추가 --- 현재 조회만 가능

@Component
@Suppress("Duplicates")
internal class PlaceReviewPersistenceAdapter(
    private val placeReviewJpaRepository: PlaceReviewJpaRepository,
    private val placeReviewImageJpaRepository: PlaceReviewImageJpaRepository,
    private val placeOneLineReviewJpaRepository: PlaceOneLineReviewJpaRepository,
    private val placeReviewPersistenceMapper: PlaceReviewPersistenceMapper,
    private val placeReviewOneLineReviewPersistenceMapper: PlaceOneLineReviewPersistenceMapper,
    private val placeOneLineReviewStatJpaRepository: PlaceOneLineReviewStatJpaRepository,
    private val placeOneLineReviewStatPersistenceMapper: PlaceOneLineReviewStatPersistenceMapper,
) : SavePlaceReviewPersistencePort,
    LoadPlaceReviewPersistencePort,
    DeletePlaceReviewPersistencePort,
    LoadPlaceOneLineReviewStatPersistencePort {
    override fun savePlaceReview(placeReview: PlaceReview): PlaceReview {
        val placeReviewEntity = placeReviewPersistenceMapper.toEntity(placeReview)
        placeReviewJpaRepository.save(placeReviewEntity)

        val existingOneLineReviews =
            placeOneLineReviewJpaRepository.findAllByPlaceReviewId(placeReviewEntity.id)
        val newOneLineReviews = placeReview.oneLineReviews.filter { newReview ->
            existingOneLineReviews.none { it.contents == newReview.contents }
        }
        val removedOneLineReviews = existingOneLineReviews.filter { existingReview ->
            placeReview.oneLineReviews.none { it.contents == existingReview.contents }
        }
        placeOneLineReviewJpaRepository.deleteAll(removedOneLineReviews)

        val placeOneLineEntities = placeOneLineReviewJpaRepository.saveAll(
            newOneLineReviews.map {
                placeReviewOneLineReviewPersistenceMapper.toEntity(
                    placeReview = placeReview,
                    placeOneLineReview = it,
                )
            },
        )
        val placeReviewImageEntities = placeReview.imageUrls.map {
            PlaceReviewImageJpaEntity(
                placeReviewId = placeReviewEntity.id,
                imageUrl = it,
            )
        }

        return placeReviewPersistenceMapper.toDomain(
            placeReviewEntity,
            placeOneLineEntities,
            placeReviewImageEntities,
        )
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview {
        val placeReviewEntity = placeReviewJpaRepository.findByIdOrNull(placeReviewId)
            ?: throw NotExistsPlaceReviewException
        val oneLineReviewJpaEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdOrderByCreatedAt(placeReviewEntity.id)
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewId(placeReviewEntity.id)

        return placeReviewPersistenceMapper.toDomain(
            placeReviewEntity,
            oneLineReviewJpaEntities,
            placeReviewImageEntities,
        )
    }

    override fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository
            .findAllByIdInOrderByCreatedAt(placeReviewIds)
        val placeOneLineReviewEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id })
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewPersistenceMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOneLineReviewEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByPlaceIdOrderByCreatedAt(placeId)
        val placeOnLineReviewEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id })
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewPersistenceMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOnLineReviewEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun getAllByUserId(userId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByUserIdOrderByCreatedAt(userId)
        val placeOneLineReviewEntity = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id })
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewPersistenceMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOneLineReviewEntity
                    .filter { it.placeReviewId == placeReviewEntity.id },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun deletePlaceReviewId(placeReviewId: Long) {
        placeReviewJpaRepository.deleteById(placeReviewId)
        placeOneLineReviewJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }

    override fun getAllStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat> {
        val placeOneLineReviewStatEntities = placeOneLineReviewStatJpaRepository.findAllByPlaceId(placeId)

        return placeOneLineReviewStatEntities.map {
            placeOneLineReviewStatPersistenceMapper.toDomain(
                placeOneLineReviewStatJpaEntity = it,
            )
        }
    }

    override fun getAllStatsByPlaceIds(placeIds: List<Long>): List<PlaceOneLineReviewStat> {
        val placeOneLineReviewStatEntities = placeOneLineReviewStatJpaRepository.findAllByPlaceIdIn(placeIds)

        return placeOneLineReviewStatEntities.map {
            placeOneLineReviewStatPersistenceMapper.toDomain(
                placeOneLineReviewStatJpaEntity = it,
            )
        }
    }
}

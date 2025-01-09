package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.place.infrastructure.storage.PlaceOneLineReviewStorageMapper
import kr.wooco.woocobe.place.infrastructure.storage.PlaceReviewStorageMapper
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.place.infrastructure.storage.repository.PlaceOneLineReviewJpaRepository
import kr.wooco.woocobe.place.infrastructure.storage.repository.PlaceReviewImageJpaRepository
import kr.wooco.woocobe.place.infrastructure.storage.repository.PlaceReviewJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
@Suppress("Duplicates")
class PlaceReviewStorageGatewayImpl(
    private val placeReviewJpaRepository: PlaceReviewJpaRepository,
    private val placeReviewImageJpaRepository: PlaceReviewImageJpaRepository,
    private val placeOneLineReviewJpaRepository: PlaceOneLineReviewJpaRepository,
    private val placeReviewStorageMapper: PlaceReviewStorageMapper,
    private val placeOneLineReviewStorageMapper: PlaceOneLineReviewStorageMapper,
) : PlaceReviewStorageGateway {
    override fun save(placeReview: PlaceReview): PlaceReview {
        val placeReviewEntity = placeReviewStorageMapper.toEntity(placeReview)
        placeReviewJpaRepository.save(placeReviewEntity)

        val existingOneLineReviews =
            placeOneLineReviewJpaRepository.findAllByPlaceReviewId(placeReviewEntity.id!!)

        val newOneLineReviews = placeReview.oneLineReviews.filter { newReview ->
            existingOneLineReviews.none { it.content == newReview.content }
        }
        val removedOneLineReviews = existingOneLineReviews.filter { existingReview ->
            placeReview.oneLineReviews.none { it.content == existingReview.content }
        }

        placeOneLineReviewJpaRepository.deleteAll(removedOneLineReviews)

        val placeOneLineEntities = placeOneLineReviewJpaRepository.saveAll(
            newOneLineReviews.map {
                placeOneLineReviewStorageMapper.toEntity(
                    placeReview = placeReview,
                    placeOneLineReview = it,
                )
            },
        )

        val placeReviewImageEntities = placeReview.imageUrls.map {
            PlaceReviewImageJpaEntity(
                placeReviewId = placeReviewEntity.id!!,
                imageUrl = it,
            )
        }

        return placeReviewStorageMapper.toDomain(
            placeReviewEntity,
            placeOneLineEntities,
            placeReviewImageEntities,
        )
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview {
        val placeReviewEntity = placeReviewJpaRepository.findByIdOrNull(placeReviewId)
            ?: throw RuntimeException()

        val oneLineReviewJpaEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdOrderByCreatedAt(placeReviewEntity.id!!)

        val placeReviewImageEntities =
            placeReviewImageJpaRepository.findAllByPlaceReviewId(placeReviewEntity.id!!)

        return placeReviewStorageMapper.toDomain(
            placeReviewEntity,
            oneLineReviewJpaEntities,
            placeReviewImageEntities,
        )
    }

    override fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository
            .findAllByIdInOrderByCreatedAt(placeReviewIds)

        val placeOneLineReviewEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id!! })

        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id!! })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewStorageMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOneLineReviewEntities
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
            )
        }
    }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByPlaceIdOrderByCreatedAt(placeId)

        val placeOnLineReviewEntities = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id!! })

        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id!! })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewStorageMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOnLineReviewEntities
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
            )
        }
    }

    override fun getAllByUserId(userId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByUserIdOrderByCreatedAt(userId)

        val placeOneLineReviewEntity = placeOneLineReviewJpaRepository
            .findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewEntities.map { it.id!! })

        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id!! })

        return placeReviewEntities.map { placeReviewEntity ->

            placeReviewStorageMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeOneLineReviewJpaEntities = placeOneLineReviewEntity
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id!! },
            )
        }
    }

    override fun deleteByPlaceReviewId(placeReviewId: Long) {
        placeReviewJpaRepository.deleteById(placeReviewId)
        placeOneLineReviewJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }
}

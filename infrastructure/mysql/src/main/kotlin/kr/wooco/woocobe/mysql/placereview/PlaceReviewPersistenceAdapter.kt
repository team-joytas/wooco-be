package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.application.port.out.DeletePlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.SavePlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.exception.NotExistsPlaceReviewException
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.mysql.placereview.repository.PlaceReviewImageJpaRepository
import kr.wooco.woocobe.mysql.placereview.repository.PlaceReviewJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
@Suppress("Duplicates")
internal class PlaceReviewPersistenceAdapter(
    private val placeReviewJpaRepository: PlaceReviewJpaRepository,
    private val placeReviewImageJpaRepository: PlaceReviewImageJpaRepository,
    private val placeReviewPersistenceMapper: PlaceReviewPersistenceMapper,
) : SavePlaceReviewPersistencePort,
    LoadPlaceReviewPersistencePort,
    DeletePlaceReviewPersistencePort {
    override fun savePlaceReview(placeReview: PlaceReview): PlaceReview {
        val placeReviewEntity = placeReviewPersistenceMapper.toEntity(placeReview)
        placeReviewJpaRepository.save(placeReviewEntity)

        val placeReviewImageEntities = placeReview.imageUrls.map {
            PlaceReviewImageJpaEntity(
                placeReviewId = placeReviewEntity.id,
                imageUrl = it,
            )
        }
        return placeReviewPersistenceMapper.toDomain(
            placeReviewEntity,
            placeReviewImageEntities,
        )
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview {
        val placeReviewEntity = placeReviewJpaRepository.findByIdOrNull(placeReviewId)
            ?: throw NotExistsPlaceReviewException
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewId(placeReviewEntity.id)

        return placeReviewPersistenceMapper.toDomain(
            placeReviewEntity,
            placeReviewImageEntities,
        )
    }

    override fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository
            .findAllByIdInOrderByCreatedAt(placeReviewIds)
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewPersistenceMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> {
        val placeReviewEntities = placeReviewJpaRepository.findAllByPlaceIdOrderByCreatedAt(placeId)
        val placeReviewImageEntities = placeReviewImageJpaRepository
            .findAllByPlaceReviewIdIn(placeReviewEntities.map { it.id })

        return placeReviewEntities.map { placeReviewEntity ->
            placeReviewPersistenceMapper.toDomain(
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
            placeReviewPersistenceMapper.toDomain(
                placeReviewJpaEntity = placeReviewEntity,
                placeReviewImageJpaEntities = placeReviewImageEntities
                    .filter { it.placeReviewId == placeReviewEntity.id },
            )
        }
    }

    override fun deletePlaceReviewId(placeReviewId: Long) {
        placeReviewJpaRepository.deleteById(placeReviewId)
        placeReviewImageJpaRepository.deleteAllByPlaceReviewId(placeReviewId)
    }
}

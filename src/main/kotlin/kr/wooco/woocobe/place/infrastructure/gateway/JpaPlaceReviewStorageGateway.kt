package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.gateway.PlaceReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.place.infrastructure.storage.PlaceJpaRepository
import kr.wooco.woocobe.place.infrastructure.storage.PlaceOneLineReviewEntity
import kr.wooco.woocobe.place.infrastructure.storage.PlaceOneLineReviewJpaRepository
import kr.wooco.woocobe.place.infrastructure.storage.PlaceReviewEntity
import kr.wooco.woocobe.place.infrastructure.storage.PlaceReviewJpaRepository
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaPlaceReviewStorageGateway(
    private val userJpaRepository: UserJpaRepository,
    private val placeJpaRepository: PlaceJpaRepository,
    private val placeReviewJpaRepository: PlaceReviewJpaRepository,
    private val placeOneLineReviewJpaRepository: PlaceOneLineReviewJpaRepository,
) : PlaceReviewStorageGateway {
    override fun save(placeReview: PlaceReview): PlaceReview {
        val placeReviewEntity = placeReviewJpaRepository.save(PlaceReviewEntity.from(placeReview))
        if (placeReview.id == 0L) {
            placeReview.oneLineReviews
                .map {
                    PlaceOneLineReviewEntity.of(
                        placeId = placeReviewEntity.placeId,
                        placeReviewId = placeReviewEntity.id!!,
                        content = it.content,
                    )
                }.also(placeOneLineReviewJpaRepository::saveAll)
        }
        return placeReview
    }

    override fun getByPlaceReviewId(placeReviewId: Long): PlaceReview? =
        placeReviewJpaRepository.findByIdOrNull(placeReviewId)?.let { placeReviewEntity ->
            placeReviewEntity.toDomain(
                user = userJpaRepository.findByIdOrNull(placeReviewEntity.userId)!!.toDomain(),
                place = placeJpaRepository.findByIdOrNull(placeReviewEntity.placeId)!!.toDomain(),
                placeOneLineReview = placeOneLineReviewJpaRepository
                    .findAllByPlaceReviewIdOrderByCreatedAt(placeReviewEntity.id!!)
                    .map { it.toDomain() },
            )
        }

    override fun getAllByPlaceReviewIds(placeReviewId: List<Long>): List<PlaceReview> =
        placeReviewJpaRepository
            .findAllByIdInOrderByCreatedAt(placeReviewId)
            .run {
                val userEntities = userJpaRepository.findAllById(map { it.userId })
                val placeEntities = placeJpaRepository.findAllById(map { it.placeId })
                val placeOneLineReviewEntities = placeOneLineReviewJpaRepository
                    .findAllByPlaceReviewIdInOrderByCreatedAt(map { it.id!! })

                map { placeReviewEntity ->
                    placeReviewEntity.toDomain(
                        user = userEntities.find { placeReviewEntity.userId == it.id }!!.toDomain(),
                        place = placeEntities
                            .find { placeReviewEntity.placeId == it.id }!!
                            .toDomain(),
                        placeOneLineReview = placeOneLineReviewEntities
                            .filter { placeReviewEntity.id == it.placeReviewId }
                            .map { it.toDomain() },
                    )
                }
            }

    override fun getAllByPlaceId(placeId: Long): List<PlaceReview> =
        placeReviewJpaRepository
            .findAllByPlaceIdOrderByCreatedAt(placeId)
            .run {
                val userEntity = userJpaRepository.findAllById(map { it.userId })
                val placeOneLineReviewEntity =
                    placeOneLineReviewJpaRepository.findAllByPlaceReviewIdInOrderByCreatedAt(map { it.id!! })

                map { placeReviewEntity ->
                    placeReviewEntity.toDomain(
                        user = userEntity.find { placeReviewEntity.userId == it.id }!!.toDomain(),
                        place = placeJpaRepository
                            .findByIdOrNull(placeReviewEntity.placeId)!!
                            .toDomain(),
                        placeOneLineReview = placeOneLineReviewEntity
                            .filter { placeReviewEntity.id == it.placeReviewId }
                            .map { it.toDomain() },
                    )
                }
            }

    override fun getAllByUserId(userId: Long): List<PlaceReview> =
        placeReviewJpaRepository
            .findAllByUserIdOrderByCreatedAt(userId)
            .run {
                val userEntity = userJpaRepository.findByIdOrNull(userId)!!
                val placeOneLineReviewEntity =
                    placeOneLineReviewJpaRepository.findAllByPlaceReviewIdInOrderByCreatedAt(map { it.id!! })

                map { placeReviewEntity ->
                    placeReviewEntity.toDomain(
                        user = userEntity.toDomain(),
                        place = placeJpaRepository
                            .findByIdOrNull(placeReviewEntity.placeId)!!
                            .toDomain(),
                        placeOneLineReview = placeOneLineReviewEntity
                            .filter { placeReviewEntity.id == it.placeReviewId }
                            .map { it.toDomain() },
                    )
                }
            }

    override fun deleteByPlaceReviewId(placeReviewId: Long) = placeReviewJpaRepository.deleteById(placeReviewId)
}

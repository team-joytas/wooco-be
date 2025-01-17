package kr.wooco.woocobe.place.infrastructure.storage

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReview
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceReviewJpaEntity
import org.springframework.stereotype.Component

@Component
class PlaceReviewStorageMapper {
    fun toDomain(
        placeReviewJpaEntity: PlaceReviewJpaEntity,
        placeOneLineReviewJpaEntities: List<PlaceOneLineReviewJpaEntity>,
        placeReviewImageJpaEntities: List<PlaceReviewImageJpaEntity>,
    ): PlaceReview =
        PlaceReview(
            id = placeReviewJpaEntity.id,
            userId = placeReviewJpaEntity.userId,
            placeId = placeReviewJpaEntity.placeId,
            rating = placeReviewJpaEntity.rating,
            contents = placeReviewJpaEntity.contents,
            writeDateTime = placeReviewJpaEntity.createdAt,
            oneLineReviews = placeOneLineReviewJpaEntities.map { PlaceOneLineReview(contents = it.contents) },
            imageUrls = placeReviewImageJpaEntities.map { it.imageUrl },
        )

    fun toEntity(placeReview: PlaceReview): PlaceReviewJpaEntity =
        PlaceReviewJpaEntity(
            id = placeReview.id,
            userId = placeReview.userId,
            placeId = placeReview.placeId,
            rating = placeReview.rating,
            contents = placeReview.contents,
        )
}

package kr.wooco.woocobe.place.infrastructure.storage

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReview
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceOneLineReviewJpaEntity
import org.springframework.stereotype.Component

@Component
class PlaceOneLineReviewStorageMapper {
    fun toDomain(placeOneLineReviewJpaEntity: PlaceOneLineReviewJpaEntity): PlaceOneLineReview =
        PlaceOneLineReview(contents = placeOneLineReviewJpaEntity.contents)

    fun toEntity(
        placeReview: PlaceReview,
        placeOneLineReview: PlaceOneLineReview,
    ): PlaceOneLineReviewJpaEntity =
        PlaceOneLineReviewJpaEntity(
            placeId = placeReview.placeId,
            placeReviewId = placeReview.id,
            contents = placeOneLineReview.contents,
        )
}

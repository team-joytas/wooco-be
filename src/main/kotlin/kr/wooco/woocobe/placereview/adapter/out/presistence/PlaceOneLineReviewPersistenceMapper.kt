package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import org.springframework.stereotype.Component

@Component
internal class PlaceOneLineReviewPersistenceMapper {
    fun toDomain(placeOneLineReviewJpaEntity: PlaceOneLineReviewJpaEntity): PlaceReview.OneLineReview =
        PlaceReview.OneLineReview(value = placeOneLineReviewJpaEntity.contents)

    fun toEntity(
        placeReview: PlaceReview,
        placeOneLineReview: PlaceReview.OneLineReview,
    ): PlaceOneLineReviewJpaEntity =
        PlaceOneLineReviewJpaEntity(
            placeId = placeReview.placeId,
            placeReviewId = placeReview.id,
            contents = placeOneLineReview.value,
        )
}

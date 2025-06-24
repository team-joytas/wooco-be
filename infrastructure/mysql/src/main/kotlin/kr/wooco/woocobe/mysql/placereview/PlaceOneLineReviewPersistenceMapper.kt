package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity

internal object PlaceOneLineReviewPersistenceMapper {
    fun toDomainEntity(placeOneLineReviewJpaEntity: PlaceOneLineReviewJpaEntity): PlaceOneLineReview =
        PlaceOneLineReview(
            id = placeOneLineReviewJpaEntity.placeReviewId,
            placeId = placeOneLineReviewJpaEntity.placeId,
            placeReviewId = placeOneLineReviewJpaEntity.placeReviewId,
            content = PlaceOneLineReview.Content(
                value = placeOneLineReviewJpaEntity.content,
            ),
        )

    fun toJpaEntity(placeOneLineReview: PlaceOneLineReview): PlaceOneLineReviewJpaEntity =
        PlaceOneLineReviewJpaEntity(
            placeId = placeOneLineReview.placeId,
            placeReviewId = placeOneLineReview.placeReviewId,
            content = placeOneLineReview.content.value,
        )
}

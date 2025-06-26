package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity

internal object PlaceOneLineReviewPersistenceMapper {
    fun toDomainEntity(placeOneLineReviewJpaEntity: PlaceOneLineReviewJpaEntity): PlaceOneLineReview =
        PlaceOneLineReview(
            id = placeOneLineReviewJpaEntity.placeReviewId,
            placeId = placeOneLineReviewJpaEntity.placeId,
            placeReviewId = placeOneLineReviewJpaEntity.placeReviewId,
            contents = PlaceOneLineReview.Contents(
                value = placeOneLineReviewJpaEntity.contents,
            ),
        )

    fun toJpaEntity(placeOneLineReview: PlaceOneLineReview): PlaceOneLineReviewJpaEntity =
        PlaceOneLineReviewJpaEntity(
            placeId = placeOneLineReview.placeId,
            placeReviewId = placeOneLineReview.placeReviewId,
            contents = placeOneLineReview.contents.value,
        )
}

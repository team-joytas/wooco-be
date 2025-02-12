package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity
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

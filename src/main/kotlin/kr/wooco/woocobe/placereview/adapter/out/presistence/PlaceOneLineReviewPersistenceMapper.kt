package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import org.springframework.stereotype.Component

@Component
internal class PlaceOneLineReviewPersistenceMapper {
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

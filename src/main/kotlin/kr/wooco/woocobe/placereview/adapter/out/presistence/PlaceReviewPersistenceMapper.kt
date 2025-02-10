package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceReviewJpaEntity
import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import org.springframework.stereotype.Component

@Component
internal class PlaceReviewPersistenceMapper {
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
            oneLineReviews = placeOneLineReviewJpaEntities.map { PlaceReview.OneLineReview(it.contents) },
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

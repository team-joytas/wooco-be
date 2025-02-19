package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewJpaEntity
import org.springframework.stereotype.Component

@Component
internal class PlaceReviewPersistenceMapper {
    fun toDomain(
        placeReviewJpaEntity: PlaceReviewJpaEntity,
        placeReviewImageJpaEntities: List<PlaceReviewImageJpaEntity>,
    ): PlaceReview =
        PlaceReview(
            id = placeReviewJpaEntity.id,
            userId = placeReviewJpaEntity.userId,
            placeId = placeReviewJpaEntity.placeId,
            rating = placeReviewJpaEntity.rating,
            contents = placeReviewJpaEntity.contents,
            writeDateTime = placeReviewJpaEntity.createdAt,
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

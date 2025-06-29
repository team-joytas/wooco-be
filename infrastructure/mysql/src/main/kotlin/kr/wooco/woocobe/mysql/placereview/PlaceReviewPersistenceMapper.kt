package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewJpaEntity

internal object PlaceReviewPersistenceMapper {
    fun toDomainEntity(
        placeReviewJpaEntity: PlaceReviewJpaEntity,
        placeReviewImageJpaEntities: List<PlaceReviewImageJpaEntity>,
    ): PlaceReview =
        PlaceReview(
            id = placeReviewJpaEntity.id,
            userId = placeReviewJpaEntity.userId,
            placeId = placeReviewJpaEntity.placeId,
            rating = PlaceReviewRating(placeReviewJpaEntity.rating),
            contents = placeReviewJpaEntity.contents,
            writeDateTime = placeReviewJpaEntity.createdAt,
            imageUrls = placeReviewImageJpaEntities.map { it.imageUrl },
            status = PlaceReview.Status.valueOf(placeReviewJpaEntity.status),
        )

    fun toJpaEntity(placeReview: PlaceReview): PlaceReviewJpaEntity =
        PlaceReviewJpaEntity(
            id = placeReview.id,
            userId = placeReview.userId,
            placeId = placeReview.placeId,
            rating = placeReview.rating.score,
            contents = placeReview.contents,
            status = placeReview.status.name,
        )
}

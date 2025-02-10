package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewStatJpaEntity
import kr.wooco.woocobe.placereview.domain.entity.PlaceOneLineReviewStat
import org.springframework.stereotype.Component

@Component
internal class PlaceOneLineReviewStatPersistenceMapper {
    fun toDomain(placeOneLineReviewStatJpaEntity: PlaceOneLineReviewStatJpaEntity): PlaceOneLineReviewStat =
        PlaceOneLineReviewStat(
            id = placeOneLineReviewStatJpaEntity.id,
            contents = PlaceOneLineReviewStat.Contents(
                value = placeOneLineReviewStatJpaEntity.contents,
            ),
            count = placeOneLineReviewStatJpaEntity.count,
        )

    fun toEntity(
        placeOneLineReviewStat: PlaceOneLineReviewStat,
        placeId: Long,
    ): PlaceOneLineReviewStatJpaEntity =
        PlaceOneLineReviewStatJpaEntity(
            id = placeOneLineReviewStat.id,
            placeId = placeId,
            contents = placeOneLineReviewStat.contents.value,
            count = placeOneLineReviewStat.count,
        )
}

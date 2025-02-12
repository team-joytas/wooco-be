package kr.wooco.woocobe.mysql.placereview

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReviewStat
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewStatJpaEntity
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

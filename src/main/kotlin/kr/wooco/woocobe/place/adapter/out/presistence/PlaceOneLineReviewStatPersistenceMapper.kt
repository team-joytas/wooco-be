package kr.wooco.woocobe.place.adapter.out.presistence

import kr.wooco.woocobe.place.domain.entity.PlaceOneLineReviewStat
import org.springframework.stereotype.Component

@Component
internal class PlaceOneLineReviewStatPersistenceMapper {
    fun toDomain(
        contents: String,
        count: Long,
    ): PlaceOneLineReviewStat =
        PlaceOneLineReviewStat(
            contents = contents,
            count = count,
        )
}

package kr.wooco.woocobe.place.infrastructure.storage

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat
import org.springframework.stereotype.Component

@Component
class PlaceOneLineReviewStatStorageMapper {
    fun toDomain(
        content: String,
        count: Long,
    ): PlaceOneLineReviewStat =
        PlaceOneLineReviewStat(
            content = content,
            count = count,
        )
}

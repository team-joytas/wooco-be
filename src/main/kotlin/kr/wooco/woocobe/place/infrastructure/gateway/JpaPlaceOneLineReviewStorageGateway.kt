package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.gateway.PlaceOneLineReviewStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat
import kr.wooco.woocobe.place.infrastructure.storage.PlaceOneLineReviewJpaRepository
import org.springframework.stereotype.Component

@Component
class JpaPlaceOneLineReviewStorageGateway(
    private val placeOneLineReviewRepository: PlaceOneLineReviewJpaRepository,
) : PlaceOneLineReviewStorageGateway {
    override fun getOneLineReviewStats(placeId: Long): List<PlaceOneLineReviewStat> {
        val stats = placeOneLineReviewRepository.findPlaceOneLineReviewStatsByPlaceId(placeId)
        return stats.map { row ->
            val content = row["content"] ?: throw RuntimeException()
            val count = row["count"] ?: throw RuntimeException()

            PlaceOneLineReviewStat(
                content = content.toString(),
                count = count,
            )
        }
    }
}

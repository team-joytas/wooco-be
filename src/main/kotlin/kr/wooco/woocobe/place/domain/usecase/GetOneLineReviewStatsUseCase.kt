package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat
import org.springframework.stereotype.Service

data class GetOneLineReviewStatsInput(
    val placeId: Long,
)

data class GetOneLineReviewStatsOutput(
    val placeOneLineReviewRank: List<PlaceOneLineReviewStat>,
)

@Service
class GetOneLineReviewStatsUseCase(
    private val placeStorageGateway: PlaceStorageGateway,
) : UseCase<GetOneLineReviewStatsInput, GetOneLineReviewStatsOutput> {
    override fun execute(input: GetOneLineReviewStatsInput): GetOneLineReviewStatsOutput {
        val placeOneLineReviewRank =
            placeStorageGateway.getOneLineReviewStats(input.placeId)

        return GetOneLineReviewStatsOutput(
            placeOneLineReviewRank = placeOneLineReviewRank,
        )
    }
}

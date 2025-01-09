package kr.wooco.woocobe.place.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

data class GetOneLineReviewStatsInput(
    val placeId: Long,
)

data class GetOneLineReviewStatsOutput(
    val placeOneLineReviewRank: List<PlaceOneLineReviewStat>,
)

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

package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.GetOneLineReviewStatsInput
import kr.wooco.woocobe.place.domain.usecase.GetOneLineReviewStatsUseCase
import kr.wooco.woocobe.place.domain.usecase.GetPlaceInput
import kr.wooco.woocobe.place.domain.usecase.GetPlaceUseCase
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceDetailResponse
import org.springframework.stereotype.Service

@Service
class PlaceQueryFacade(
    private val getPlaceUseCase: GetPlaceUseCase,
    private val getOneLineReviewStatsUseCase: GetOneLineReviewStatsUseCase,
) {
    fun getPlaceDetail(placeId: Long): PlaceDetailResponse {
        val getPlaceResult = getPlaceUseCase.execute(GetPlaceInput(placeId))

        val getOneLineReviewStatsResult =
            getOneLineReviewStatsUseCase.execute(GetOneLineReviewStatsInput(placeId = placeId))

        return PlaceDetailResponse.of(
            place = getPlaceResult.place,
            placeOneLineReviewStats = getOneLineReviewStatsResult.placeOneLineReviewStats,
        )
    }
}

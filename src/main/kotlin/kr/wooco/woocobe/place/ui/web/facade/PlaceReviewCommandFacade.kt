package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.AddPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.DeletePlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.DeletePlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceReviewResponse
import org.springframework.stereotype.Service

@Service
class PlaceReviewCommandFacade(
    private val addPlaceReviewUseCase: AddPlaceReviewUseCase,
    private val updatePlaceReviewUseCase: UpdatePlaceReviewUseCase,
    private val deletePlaceReviewUseCase: DeletePlaceReviewUseCase,
) {
    fun createPlaceReview(
        userId: Long,
        placeId: Long,
        request: CreatePlaceReviewRequest,
    ): CreatePlaceReviewResponse {
        val addPlaceReviewResult = addPlaceReviewUseCase.execute(request.toCommand(userId = userId, placeId = placeId))

        return CreatePlaceReviewResponse(addPlaceReviewResult.placeReviewId)
    }

    fun updatePlaceReview(
        userId: Long,
        placeReviewId: Long,
        request: UpdatePlaceReviewRequest,
    ) = updatePlaceReviewUseCase.execute(request.toCommand(userId = userId, placeReviewId = placeReviewId))

    fun deletePlaceReview(
        userId: Long,
        placeReviewId: Long,
    ) = deletePlaceReviewUseCase.execute(DeletePlaceReviewInput(userId = userId, placeReviewId = placeReviewId))
}

package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.AddPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.AddPlaceUseCase
import kr.wooco.woocobe.place.domain.usecase.DeletePlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.DeletePlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceUseCase
import kr.wooco.woocobe.place.domain.usecase.GetOneLineReviewStatsUseCase
import kr.wooco.woocobe.place.domain.usecase.GetPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.GetPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetPlaceUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllMyPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.request.UpdatePlaceReviewRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceReviewResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceReviewDetailsResponse
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import org.springframework.stereotype.Service

@Service
class PlaceReviewFacade(
    private val addPlaceUseCase: AddPlaceUseCase,
    private val addPlaceReviewUseCase: AddPlaceReviewUseCase,
    private val updatePlaceReviewUseCase: UpdatePlaceReviewUseCase,
    private val deletePlaceReviewUseCase: DeletePlaceReviewUseCase,
    private val getPlaceUseCase: GetPlaceUseCase,
    private val getPlaceReviewUseCase: GetPlaceReviewUseCase,
    private val getAllPlaceUseCase: GetAllPlaceUseCase,
    private val getAllPlaceReviewUseCase: GetAllPlaceReviewUseCase,
    private val getOneLineReviewStatsUseCase: GetOneLineReviewStatsUseCase,
    private val getAllMyPlaceReviewUsecase: GetAllMyPlaceReviewUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
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

    fun getPlaceReviewDetail(placeReviewId: Long): PlaceReviewDetailsResponse {
        val getPlaceReviewResult = getPlaceReviewUseCase.execute(GetPlaceReviewInput(placeReviewId = placeReviewId))

        val getUserResult = getUserUseCase.execute(GetUserInput(userId = getPlaceReviewResult.placeReview.userId))

        return PlaceReviewDetailsResponse.of(
            placeReview = getPlaceReviewResult.placeReview,
            user = getUserResult.user,
        )
    }

    fun getAllPlaceReviewDetail(placeId: Long): List<PlaceReviewDetailsResponse> {
        val getAllPlaceReviewResult = getAllPlaceReviewUseCase.execute(GetAllPlaceReviewInput(placeId = placeId))

        val userIds = getAllPlaceReviewResult.placeReviews.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = userIds))

        return PlaceReviewDetailsResponse.listOf(
            placeReviews = getAllPlaceReviewResult.placeReviews,
            users = getAllUserResult.users,
        )
    }

    fun getAllUserWith
}

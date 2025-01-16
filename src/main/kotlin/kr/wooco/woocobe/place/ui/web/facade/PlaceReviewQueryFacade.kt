package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.AddPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.DeletePlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllMyPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.GetPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetUserAllPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceReviewDetailsResponse
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import org.springframework.stereotype.Service

@Service
class PlaceReviewQueryFacade(
    private val addPlaceReviewUseCase: AddPlaceReviewUseCase,
    private val updatePlaceReviewUseCase: UpdatePlaceReviewUseCase,
    private val deletePlaceReviewUseCase: DeletePlaceReviewUseCase,
    private val getPlaceReviewUseCase: GetPlaceReviewUseCase,
    private val getAllPlaceReviewUseCase: GetAllPlaceReviewUseCase,
    private val getAllMyPlaceReviewUseCase: GetAllMyPlaceReviewUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
) {
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

    fun getAllMyPlaceReview(userId: Long): List<PlaceReviewDetailsResponse> {
        val getAllMyPlaceReviewResult = getAllMyPlaceReviewUseCase.execute(GetUserAllPlaceReviewInput(userId = userId))

        val getUserResult = getUserUseCase.execute(GetUserInput(userId = userId))

        return PlaceReviewDetailsResponse.listOf(
            placeReviews = getAllMyPlaceReviewResult.placeReviews,
            users = listOf(getUserResult.user),
        )
    }
}

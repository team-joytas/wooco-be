package kr.wooco.woocobe.place.ui.web.facade

import kr.wooco.woocobe.place.domain.usecase.AddPlaceUseCase
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewUseCase
import kr.wooco.woocobe.place.domain.usecase.GetPlaceInput
import kr.wooco.woocobe.place.domain.usecase.GetPlaceUseCase
import kr.wooco.woocobe.place.ui.web.controller.request.CreatePlaceRequest
import kr.wooco.woocobe.place.ui.web.controller.response.CreatePlaceResponse
import kr.wooco.woocobe.place.ui.web.controller.response.PlaceDetailResponse
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import org.springframework.stereotype.Service

@Service
class PlaceFacade(
    private val addPlaceUseCase: AddPlaceUseCase,
    private val getPlaceUseCase: GetPlaceUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
    private val getAllPlaceReviewUseCase: GetAllPlaceReviewUseCase,
) {
    fun createPlace(
        userId: Long,
        request: CreatePlaceRequest,
    ): CreatePlaceResponse {
        val createPlaceResult = addPlaceUseCase.execute(request.toCommand(userId))
        return CreatePlaceResponse(createPlaceResult.placeId)
    }

    // TODO: 장소 상세 조회 시 함께 조회될 리뷰 갯수 제한
    fun getPlaceDetail(placeId: Long): PlaceDetailResponse {
        val getPlaceResult = getPlaceUseCase.execute(GetPlaceInput(placeId))
        val getAllPlaceReviewResult = getAllPlaceReviewUseCase.execute(GetAllPlaceReviewInput(placeId = placeId))

        val userIds = getAllPlaceReviewResult.placeReviews.map { it.userId }
        val userUseCaseResult = getAllUserUseCase.execute(GetAllUserInput(userIds = userIds))

        return PlaceDetailResponse.of(
            getPlaceResult.place,
            getAllPlaceReviewResult.placeReviews,
            userUseCaseResult.users,
        )
    }
}

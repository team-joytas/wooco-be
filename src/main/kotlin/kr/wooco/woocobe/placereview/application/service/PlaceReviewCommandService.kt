package kr.wooco.woocobe.placereview.application.service

import kr.wooco.woocobe.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.placereview.application.port.out.DeletePlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.application.port.out.SavePlaceReviewPersistencePort
import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceReviewCommandService(
    private val loadPlaceReviewPersistencePort: LoadPlaceReviewPersistencePort,
    private val savePlaceReviewPersistencePort: SavePlaceReviewPersistencePort,
    private val deletePlaceReviewPersistencePort: DeletePlaceReviewPersistencePort,
) : CreatePlaceReviewUseCase,
    UpdatePlaceReviewUseCase,
    DeletePlaceReviewUseCase {
    // TODO : 리뷰 추가시 이벤트 발생 --> 장소 리뷰 수 증가, 장소 평점 업데이트
    @Transactional
    override fun createPlaceReview(command: CreatePlaceReviewUseCase.Command): Long {
        val placeReview = PlaceReview.create(
            userId = command.userId,
            placeId = command.placeId,
            rating = command.rating,
            contents = command.contents,
            oneLineReviews = command.oneLineReviews,
            imageUrls = command.imageUrls,
        )
        return savePlaceReviewPersistencePort.savePlaceReview(placeReview).id
    }

    // TODO : 리뷰 수정시 이벤트 발생 --> 장소 평점 업데이트
    @Transactional
    override fun updatePlaceReview(command: UpdatePlaceReviewUseCase.Command) {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(command.placeReviewId)
        placeReview.update(
            userId = command.userId,
            rating = command.rating,
            contents = command.contents,
            oneLineReviews = command.oneLineReviews,
            imageUrls = command.imageUrls,
        )
        savePlaceReviewPersistencePort.savePlaceReview(placeReview)
    }

    // TODO : 리뷰 삭제시 이벤트 발생 --> 장소 리뷰 수 감소, 장소 평점 업데이트
    @Transactional
    override fun deletePlaceReview(command: DeletePlaceReviewUseCase.Command) {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(command.placeReviewId)
        placeReview.isValidWriter(command.userId)
        deletePlaceReviewPersistencePort.deletePlaceReviewId(command.placeReviewId)
    }
}

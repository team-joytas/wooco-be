package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceReviewCommandService(
    private val placeReviewQueryPort: PlaceReviewQueryPort,
    private val placeReviewCommandPort: PlaceReviewCommandPort,
) : CreatePlaceReviewUseCase,
    UpdatePlaceReviewUseCase,
    DeletePlaceReviewUseCase {
    @Transactional
    override fun createPlaceReview(command: CreatePlaceReviewUseCase.Command): Long {
        val placeReview = PlaceReview.create(command.toCreateCommand()) { placeReview ->
            placeReviewCommandPort.savePlaceReview(placeReview)
        }
        return placeReview.id
    }

    @Transactional
    override fun updatePlaceReview(command: UpdatePlaceReviewUseCase.Command) {
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(command.placeReviewId)
        val updatedPlaceReview = placeReview.update(command.toUpdateCommand())
        placeReviewCommandPort.savePlaceReview(updatedPlaceReview)
    }

    @Transactional
    override fun deletePlaceReview(command: DeletePlaceReviewUseCase.Command) {
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(command.placeReviewId)
        val deletePlaceReview = placeReview.delete(command.toDeleteCommand())
        placeReviewCommandPort.savePlaceReview(deletePlaceReview)
        placeReviewCommandPort.deleteAllByPlaceReviewId(placeReview.id)
    }
}

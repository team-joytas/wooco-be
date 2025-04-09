package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReviewRating
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceReviewCommandService(
    private val placeReviewQueryPort: PlaceReviewQueryPort,
    private val placeReviewCommandPort: PlaceReviewCommandPort,
    private val eventPublisher: ApplicationEventPublisher,
) : CreatePlaceReviewUseCase,
    UpdatePlaceReviewUseCase,
    DeletePlaceReviewUseCase {
    @Transactional
    override fun createPlaceReview(command: CreatePlaceReviewUseCase.Command): Long {
        val placeReview = placeReviewCommandPort.savePlaceReview(
            PlaceReview.create(
                userId = command.userId,
                placeId = command.placeId,
                rating = PlaceReviewRating(command.rating),
                contents = command.contents,
                imageUrls = command.imageUrls,
            ),
        )
        placeReviewCommandPort.saveAllPlaceOneLineReview(
            PlaceOneLineReview.create(
                placeId = command.placeId,
                placeReviewId = placeReview.id,
                contentsList = command.oneLineReviews,
            ),
        )

        eventPublisher.publishEvent(PlaceReviewCreateEvent.from(placeReview))
        return placeReview.id
    }

    @Transactional
    override fun updatePlaceReview(command: UpdatePlaceReviewUseCase.Command) {
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(command.placeReviewId)
        placeReviewCommandPort.savePlaceReview(
            placeReview.update(
                userId = command.userId,
                rating = PlaceReviewRating(command.rating),
                contents = command.contents,
                imageUrls = command.imageUrls,
            ),
        )

        placeReviewCommandPort.deleteAllByPlaceReviewId(placeReview.id)
        placeReviewCommandPort.saveAllPlaceOneLineReview(
            PlaceOneLineReview.create(
                placeId = placeReview.placeId,
                placeReviewId = placeReview.id,
                contentsList = command.oneLineReviews,
            ),
        )

        eventPublisher.publishEvent(PlaceReviewUpdateEvent.from(placeReview))
    }

    @Transactional
    override fun deletePlaceReview(command: DeletePlaceReviewUseCase.Command) {
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(command.placeReviewId)
        placeReview.isValidWriter(command.userId)
        placeReviewCommandPort.deleteAllByPlaceReviewId(placeReview.id)
        placeReviewCommandPort.deletePlaceReviewId(command.placeReviewId)

        eventPublisher.publishEvent(PlaceReviewDeleteEvent.from(placeReview))
    }
}

package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import kr.wooco.woocobe.core.placereview.domain.exception.TooManyImagesException
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
        validateImageCount(command.imageUrls.size)
        val placeReview = placeReviewCommandPort.savePlaceReview(
            PlaceReview.create(
                userId = command.userId,
                placeId = command.placeId,
                rating = command.rating,
                contents = command.contents,
                imageUrls = command.imageUrls,
            ),
        )
        val placeOneLineReviews = command.oneLineReviews.map { contents ->
            PlaceOneLineReview.create(
                placeId = command.placeId,
                placeReviewId = placeReview.id,
                contents = contents,
            )
        }
        placeReviewCommandPort.saveAllPlaceOneLineReview(placeOneLineReviews)

        eventPublisher.publishEvent(PlaceReviewCreateEvent.from(placeReview))
        return placeReview.id
    }

    @Transactional
    override fun updatePlaceReview(command: UpdatePlaceReviewUseCase.Command) {
        validateImageCount(command.imageUrls.size)
        val placeReview = placeReviewQueryPort.getByPlaceReviewId(command.placeReviewId)
        placeReviewCommandPort.savePlaceReview(
            placeReview.update(
                userId = command.userId,
                rating = command.rating,
                contents = command.contents,
                imageUrls = command.imageUrls,
            ),
        )

        placeReviewCommandPort.deleteAllByPlaceReviewId(placeReview.id)
        val placeOneLineReviews = command.oneLineReviews.map { contents ->
            PlaceOneLineReview.create(
                placeId = placeReview.placeId,
                placeReviewId = placeReview.id,
                contents = contents,
            )
        }
        placeReviewCommandPort.saveAllPlaceOneLineReview(placeOneLineReviews)

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

    private fun validateImageCount(imageUrlsSize: Int) {
        if (imageUrlsSize > 10) {
            throw TooManyImagesException
        }
    }
}

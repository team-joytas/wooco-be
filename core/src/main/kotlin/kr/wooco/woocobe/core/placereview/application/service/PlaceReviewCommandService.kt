package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.DeletePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.out.DeleteAllPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.DeletePlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.SaveAllPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.SavePlaceReviewPersistencePort
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceReviewCommandService(
    private val loadPlaceReviewPersistencePort: LoadPlaceReviewPersistencePort,
    private val savePlaceReviewPersistencePort: SavePlaceReviewPersistencePort,
    private val deletePlaceReviewPersistencePort: DeletePlaceReviewPersistencePort,
    private val saveAllPlaceOneLineReviewPersistencePort: SaveAllPlaceOneLineReviewPersistencePort,
    private val deleteAllPlaceOneLineReviewPersistencePort: DeleteAllPlaceOneLineReviewPersistencePort,
    private val eventPublisher: ApplicationEventPublisher,
) : CreatePlaceReviewUseCase,
    UpdatePlaceReviewUseCase,
    DeletePlaceReviewUseCase {
    @Transactional
    override fun createPlaceReview(command: CreatePlaceReviewUseCase.Command): Long {
        val placeReview = savePlaceReviewPersistencePort.savePlaceReview(
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
        saveAllPlaceOneLineReviewPersistencePort.saveAllPlaceOneLineReview(placeOneLineReviews)

        eventPublisher.publishEvent(PlaceReviewCreateEvent.from(placeReview))
        return placeReview.id
    }

    @Transactional
    override fun updatePlaceReview(command: UpdatePlaceReviewUseCase.Command) {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(command.placeReviewId)
        placeReview.update(
            userId = command.userId,
            rating = command.rating,
            contents = command.contents,
            imageUrls = command.imageUrls,
        )
        savePlaceReviewPersistencePort.savePlaceReview(placeReview)

        deleteAllPlaceOneLineReviewPersistencePort.deleteAllByPlaceReviewId(placeReview.id)
        val placeOneLineReviews = command.oneLineReviews.map { contents ->
            PlaceOneLineReview.create(
                placeId = placeReview.placeId,
                placeReviewId = placeReview.id,
                contents = contents,
            )
        }
        saveAllPlaceOneLineReviewPersistencePort.saveAllPlaceOneLineReview(placeOneLineReviews)

        eventPublisher.publishEvent(PlaceReviewUpdateEvent.of(placeReview, command))
    }

    @Transactional
    override fun deletePlaceReview(command: DeletePlaceReviewUseCase.Command) {
        val placeReview = loadPlaceReviewPersistencePort.getByPlaceReviewId(command.placeReviewId)
        placeReview.isValidWriter(command.userId)
        deleteAllPlaceOneLineReviewPersistencePort.deleteAllByPlaceReviewId(placeReview.id)
        deletePlaceReviewPersistencePort.deletePlaceReviewId(command.placeReviewId)

        eventPublisher.publishEvent(PlaceReviewDeleteEvent.of(placeReview))
    }
}

package kr.wooco.woocobe.core.place.application.handler

import kr.wooco.woocobe.core.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.core.place.application.port.out.SavePlacePersistencePort
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class PlaceEventHandler(
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
    private val savePlacePersistencePort: SavePlacePersistencePort,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewCreateEvent(event: PlaceReviewCreateEvent) {
        val place = loadPlacePersistencePort
            .getByPlaceId(event.placeId)
            .increaseReviewCounts()
            .processPlaceStats(currentReviewRate = 0.0, reviewRate = event.rating, reviewCountOffset = 1)
        savePlacePersistencePort.savePlace(place)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewUpdateEvent(event: PlaceReviewUpdateEvent) {
        val place = loadPlacePersistencePort
            .getByPlaceId(event.placeId)
            .processPlaceStats(currentReviewRate = event.oldRating, reviewRate = event.newRating, reviewCountOffset = 0)
        savePlacePersistencePort.savePlace(place)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewDeleteEvent(event: PlaceReviewDeleteEvent) {
        val place = loadPlacePersistencePort
            .getByPlaceId(event.placeId)
            .decreaseReviewCounts()
            .processPlaceStats(currentReviewRate = event.rating, reviewRate = 0.0, reviewCountOffset = 1)

        savePlacePersistencePort.savePlace(place)
    }
}

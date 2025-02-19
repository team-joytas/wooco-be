package kr.wooco.woocobe.core.placereview.application.handler

import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.SaveAllPlaceOneLineReviewPersistencePort
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class PlaceOneLineReviewStatsEventHandler(
    private val loadPlaceOneLineReviewPersistencePort: LoadPlaceOneLineReviewPersistencePort,
    private val saveAllPlaceOneLineReviewPersistencePort: SaveAllPlaceOneLineReviewPersistencePort,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleReviewCreateEvent(event: PlaceReviewCreateEvent) {
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleReviewUpdateEvent(event: PlaceReviewUpdateEvent) {
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleReviewDeleteEvent(event: PlaceReviewDeleteEvent) {
    }
}

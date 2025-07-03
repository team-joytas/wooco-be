package kr.wooco.woocobe.core.place.application.handler

import kr.wooco.woocobe.core.place.application.port.`in`.UpdateAverageRatingUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdatePlaceImageUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdateReviewStatsUseCase
import kr.wooco.woocobe.core.place.domain.event.PlaceCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreatedEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeletedEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class PlaceEventHandler(
    private val updatePlaceImageUseCase: UpdatePlaceImageUseCase,
    private val updateReviewStatsUseCase: UpdateReviewStatsUseCase,
    private val updateAverageRatingUseCase: UpdateAverageRatingUseCase,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePlaceCreateEvent(event: PlaceCreateEvent) {
        updatePlaceImageUseCase.updatePlaceImage(UpdatePlaceImageUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewCreateEvent(event: PlaceReviewCreatedEvent) {
        updateReviewStatsUseCase.updateReviewStats(UpdateReviewStatsUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewUpdateEvent(event: PlaceReviewUpdatedEvent) {
        updateAverageRatingUseCase.updateAverageRating(UpdateAverageRatingUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewDeleteEvent(event: PlaceReviewDeletedEvent) {
        updateReviewStatsUseCase.updateReviewStats(UpdateReviewStatsUseCase.Command(event.placeId))
    }
}

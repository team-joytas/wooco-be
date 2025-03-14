package kr.wooco.woocobe.core.place.application.handler

import kr.wooco.woocobe.core.place.application.port.`in`.DecreaseReviewCountUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.IncreasePlaceReviewCountUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ProcessAverageRatingUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdatePlaceImageUseCase
import kr.wooco.woocobe.core.place.domain.event.PlaceCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreateEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeleteEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdateEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class PlaceEventHandler(
    private val updatePlaceImageUseCase: UpdatePlaceImageUseCase,
    private val increasePlaceReviewCountUseCase: IncreasePlaceReviewCountUseCase,
    private val decreaseReviewCountUseCase: DecreaseReviewCountUseCase,
    private val processAverageRatingUseCase: ProcessAverageRatingUseCase,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePlaceCreateEvent(event: PlaceCreateEvent) {
        updatePlaceImageUseCase.updatePlaceImage(UpdatePlaceImageUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewCreateEvent(event: PlaceReviewCreateEvent) {
        increasePlaceReviewCountUseCase.increasePlaceReviewCount(IncreasePlaceReviewCountUseCase.Command(event.placeId))
        processAverageRatingUseCase.processAverageRating(ProcessAverageRatingUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewUpdateEvent(event: PlaceReviewUpdateEvent) {
        processAverageRatingUseCase.processAverageRating(ProcessAverageRatingUseCase.Command(event.placeId))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handlePlaceReviewDeleteEvent(event: PlaceReviewDeleteEvent) {
        decreaseReviewCountUseCase.decreaseReviewCount(DecreaseReviewCountUseCase.Command(event.placeId))
        processAverageRatingUseCase.processAverageRating(ProcessAverageRatingUseCase.Command(event.placeId))
    }
}

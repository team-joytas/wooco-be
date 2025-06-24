package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewCreateEvent(
    override val aggregateId: Long,
    val placeId: Long,
) : DomainEvent() {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewCreateEvent =
            PlaceReviewCreateEvent(
                aggregateId = placeReview.id,
                placeId = placeReview.placeId,
            )
    }
}

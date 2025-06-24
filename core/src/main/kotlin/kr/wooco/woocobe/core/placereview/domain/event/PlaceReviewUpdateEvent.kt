package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewUpdateEvent(
    override val aggregateId: Long,
    val placeId: Long,
) : DomainEvent() {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewUpdateEvent =
            PlaceReviewUpdateEvent(
                aggregateId = placeReview.id,
                placeId = placeReview.placeId,
            )
    }
}

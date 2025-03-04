package kr.wooco.woocobe.core.placereview.application.port.out

interface DeletePlaceReviewPersistencePort {
    fun deletePlaceReviewId(placeReviewId: Long)
}

package kr.wooco.woocobe.placereview.application.port.out

interface DeletePlaceReviewPersistencePort {
    fun deletePlaceReviewId(placeReviewId: Long)
}

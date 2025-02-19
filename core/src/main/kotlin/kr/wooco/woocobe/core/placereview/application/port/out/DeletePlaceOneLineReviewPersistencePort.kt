package kr.wooco.woocobe.core.placereview.application.port.out

interface DeletePlaceOneLineReviewPersistencePort {
    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}

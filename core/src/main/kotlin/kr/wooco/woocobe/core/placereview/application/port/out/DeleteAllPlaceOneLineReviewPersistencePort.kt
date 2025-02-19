package kr.wooco.woocobe.core.placereview.application.port.out

interface DeleteAllPlaceOneLineReviewPersistencePort {
    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}

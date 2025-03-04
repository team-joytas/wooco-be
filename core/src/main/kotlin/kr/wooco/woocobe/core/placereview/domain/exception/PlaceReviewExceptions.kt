package kr.wooco.woocobe.core.placereview.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BasePlaceReviewException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object InvalidPlaceReviewWriterException : BasePlaceReviewException(
    code = "INVALID_PLACE_REVIEW_WRITER",
    message = "해당 장소 리뷰의 작성자가 아닙니다.",
) {
    private fun readResolve(): Any = InvalidPlaceReviewWriterException
}

data object NotExistsPlaceReviewException : BasePlaceReviewException(
    code = "NOT_EXISTS_PLACE_REVIEW",
    message = "존재하지 않는 장소 리뷰입니다.",
) {
    private fun readResolve(): Any = NotExistsPlaceReviewException
}

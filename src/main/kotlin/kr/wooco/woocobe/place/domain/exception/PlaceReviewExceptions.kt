package kr.wooco.woocobe.place.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BasePlaceReviewException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object InvalidPlaceReviewWriterException : BasePlaceReviewException(
    code = "INVALID_PLACE_REVIEW_WRITER",
    message = "해당 장소 리뷰의 작성자가 아닙니다.",
    status = HttpStatus.FORBIDDEN,
) {
    private fun readResolve(): Any = InvalidPlaceReviewWriterException
}

data object NotExistsPlaceReviewException : BasePlaceReviewException(
    code = "NOT_EXISTS_PLACE_REVIEW",
    message = "존재하지 않는 장소 리뷰입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsPlaceReviewException
}

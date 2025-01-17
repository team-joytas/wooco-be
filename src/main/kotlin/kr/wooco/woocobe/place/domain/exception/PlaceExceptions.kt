package kr.wooco.woocobe.place.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BasePlaceException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object NotExistsPlaceException : BasePlaceException(
    code = "NOT_EXISTS_PLACE",
    message = "존재하지 않는 장소입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsPlaceException
}

data object MissingPlaceOneLineReviewContentException : BasePlaceException(
    code = "MISSING_PLACE_ONE_LINE_REVIEW_CONTENT",
    message = "장소 한줄평 내용이 없습니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = MissingPlaceOneLineReviewContentException
}

data object MissingPlaceOneLineReviewCountException : BasePlaceException(
    code = "MISSING_PLACE_ONE_LINE_REVIEW_COUNT",
    message = "장소 한줄평 개수가 없습니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = MissingPlaceOneLineReviewCountException
}

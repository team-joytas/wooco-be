package kr.wooco.woocobe.core.place.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BasePlaceException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsPlaceException : BasePlaceException(
    code = "NOT_EXISTS_PLACE",
    message = "존재하지 않는 장소입니다.",
) {
    private fun readResolve(): Any = NotExistsPlaceException
}

data object MissingPlaceOneLineReviewContentsException : BasePlaceException(
    code = "MISSING_PLACE_ONE_LINE_REVIEW_CONTENTS",
    message = "장소 한줄평 내용이 없습니다.",
) {
    private fun readResolve(): Any = MissingPlaceOneLineReviewContentsException
}

data object MissingPlaceOneLineReviewCountException : BasePlaceException(
    code = "MISSING_PLACE_ONE_LINE_REVIEW_COUNT",
    message = "장소 한줄평 개수가 없습니다.",
) {
    private fun readResolve(): Any = MissingPlaceOneLineReviewCountException
}

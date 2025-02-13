package kr.wooco.woocobe.core.course.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseCourseException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object InvalidCourseWriterException : BaseCourseException(
    code = "INVALID_COURSE_WRITER",
    message = "해당 코스의 작성자가 아닙니다.",
) {
    private fun readResolve(): Any = InvalidCourseWriterException
}

data object NotExistsCourseException : BaseCourseException(
    code = "NOT_EXISTS_COURSE",
    message = "존재하지 않는 코스입니다.",
) {
    private fun readResolve(): Any = NotExistsCourseException
}

data object NotExistsInterestCourseException : BaseCourseException(
    code = "NOT_EXISTS_INTEREST_COURSE",
    message = "좋아요를 누르지 않은 코스입니다.",
) {
    private fun readResolve(): Any = NotExistsInterestCourseException
}

data object AlreadyLikedCourseException : BaseCourseException(
    code = "ALREADY_LIKED_COURSE",
    message = "이미 좋아요를 누른 코스입니다.",
) {
    private fun readResolve(): Any = AlreadyLikedCourseException
}

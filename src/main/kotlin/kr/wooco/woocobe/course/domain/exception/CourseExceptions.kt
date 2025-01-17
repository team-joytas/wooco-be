package kr.wooco.woocobe.course.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BaseCourseException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object InvalidCourseWriterException : BaseCourseException(
    code = "INVALID_COURSE_WRITER",
    message = "해당 코스의 작성자가 아닙니다.",
    status = HttpStatus.FORBIDDEN,
) {
    private fun readResolve(): Any = InvalidCourseWriterException
}

data object NotExistsCourseException : BaseCourseException(
    code = "NOT_EXISTS_COURSE",
    message = "존재하지 않는 코스입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsCourseException
}

data object UnSupportCategoryException : BaseCourseException(
    code = "UN_SUPPORT_CATEGORY",
    message = "지원하지 않는 카테고리 형식입니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = UnSupportCategoryException
}

data object UnSupportSortConditionException : BaseCourseException(
    code = "UN_SUPPORT_SORT_CONDITION",
    message = "지원하지 않는 정렬 조건입니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = UnSupportSortConditionException
}

data object AlreadyLikedCourseException : BaseCourseException(
    code = "ALREADY_LIKED_COURSE",
    message = "이미 좋아요를 누른 코스입니다.",
    status = HttpStatus.CONFLICT,
) {
    private fun readResolve(): Any = AlreadyLikedCourseException
}

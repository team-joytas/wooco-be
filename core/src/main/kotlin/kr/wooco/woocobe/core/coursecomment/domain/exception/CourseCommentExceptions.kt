package kr.wooco.woocobe.core.coursecomment.domain.exception

import kr.wooco.woocobe.core.common.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BaseCourseCommentException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object InvalidCommentWriterException : BaseCourseCommentException(
    code = "INVALID_COMMENT_WRITER",
    message = "해당 댓글의 작성자가 아닙니다.",
    status = HttpStatus.FORBIDDEN,
) {
    private fun readResolve(): Any = InvalidCommentWriterException
}

data object NotExistsCommentException : BaseCourseCommentException(
    code = "NOT_EXISTS_COMMENT",
    message = "존재하지 않는 댓글입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsCommentException
}

package kr.wooco.woocobe.core.coursecomment.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseCourseCommentException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object InvalidCommentWriterException : BaseCourseCommentException(
    code = "INVALID_COMMENT_WRITER",
    message = "해당 댓글의 작성자가 아닙니다.",
) {
    private fun readResolve(): Any = InvalidCommentWriterException
}

data object NotExistsCommentException : BaseCourseCommentException(
    code = "NOT_EXISTS_COMMENT",
    message = "존재하지 않는 댓글입니다.",
) {
    private fun readResolve(): Any = NotExistsCommentException
}

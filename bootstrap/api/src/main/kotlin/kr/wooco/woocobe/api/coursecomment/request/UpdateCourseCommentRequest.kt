package kr.wooco.woocobe.api.coursecomment.request

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.UpdateCourseCommentUseCase

data class UpdateCourseCommentRequest(
    val contents: String,
) {
    fun toCommand(
        userId: Long,
        commentId: Long,
    ): UpdateCourseCommentUseCase.Command =
        UpdateCourseCommentUseCase.Command(
            courseCommentId = commentId,
            userId = userId,
            contents = contents,
        )
}

package kr.wooco.woocobe.coursecomment.adapter.`in`.web.request

import kr.wooco.woocobe.coursecomment.application.port.`in`.UpdateCourseCommentUseCase

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

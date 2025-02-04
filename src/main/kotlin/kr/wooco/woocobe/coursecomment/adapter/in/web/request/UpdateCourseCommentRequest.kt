package kr.wooco.woocobe.course.ui.web.controller.request

import kr.wooco.woocobe.course.domain.usecase.UpdateCourseCommentInput

data class UpdateCourseCommentRequest(
    val contents: String,
) {
    fun toCommand(
        userId: Long,
        commentId: Long,
    ): UpdateCourseCommentInput =
        UpdateCourseCommentInput(
            userId = userId,
            commentId = commentId,
            contents = contents,
        )
}

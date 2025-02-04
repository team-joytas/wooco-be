package kr.wooco.woocobe.course.ui.web.controller.request

import kr.wooco.woocobe.course.domain.usecase.AddCourseCommentInput

data class CreateCourseCommentRequest(
    val contents: String,
) {
    fun toCommand(
        userId: Long,
        courseId: Long,
    ): AddCourseCommentInput =
        AddCourseCommentInput(
            userId = userId,
            courseId = courseId,
            contents = contents,
        )
}

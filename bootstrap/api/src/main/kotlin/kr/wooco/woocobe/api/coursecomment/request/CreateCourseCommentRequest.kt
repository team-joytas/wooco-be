package kr.wooco.woocobe.api.coursecomment.request

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.CreateCourseCommentUseCase

data class CreateCourseCommentRequest(
    val contents: String,
) {
    fun toCommand(
        userId: Long,
        courseId: Long,
    ): CreateCourseCommentUseCase.Command =
        CreateCourseCommentUseCase.Command(
            userId = userId,
            courseId = courseId,
            contents = contents,
        )
}

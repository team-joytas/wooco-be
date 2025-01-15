package kr.wooco.woocobe.course.ui.web.facade

import kr.wooco.woocobe.course.domain.usecase.AddCourseCommentUseCase
import kr.wooco.woocobe.course.domain.usecase.DeleteCourseCommentInput
import kr.wooco.woocobe.course.domain.usecase.DeleteCourseCommentUseCase
import kr.wooco.woocobe.course.domain.usecase.UpdateCourseCommentUseCase
import kr.wooco.woocobe.course.ui.web.controller.request.CreateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.request.UpdateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.response.CreateCourseCommentResponse
import org.springframework.stereotype.Service

@Service
class CourseCommentCommandFacade(
    private val addCourseCommentUseCase: AddCourseCommentUseCase,
    private val updateCourseCommentUseCase: UpdateCourseCommentUseCase,
    private val deleteCourseCommentUseCase: DeleteCourseCommentUseCase,
) {
    fun createCourseComment(
        userId: Long,
        courseId: Long,
        request: CreateCourseCommentRequest,
    ): CreateCourseCommentResponse {
        val addCourseCommentResult =
            addCourseCommentUseCase.execute(request.toCommand(userId = userId, courseId = courseId))
        return CreateCourseCommentResponse(addCourseCommentResult.commentId)
    }

    fun updateCourseComment(
        userId: Long,
        commentId: Long,
        request: UpdateCourseCommentRequest,
    ) = updateCourseCommentUseCase.execute(request.toCommand(userId = userId, commentId = commentId))

    fun deleteCourseComment(
        userId: Long,
        commentId: Long,
    ) = deleteCourseCommentUseCase.execute(DeleteCourseCommentInput(userId = userId, commentId = commentId))
}

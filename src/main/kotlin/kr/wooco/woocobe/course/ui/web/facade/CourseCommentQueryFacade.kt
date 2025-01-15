package kr.wooco.woocobe.course.ui.web.facade

import kr.wooco.woocobe.course.domain.usecase.GetAllCourseCommentInput
import kr.wooco.woocobe.course.domain.usecase.GetAllCourseCommentUseCase
import kr.wooco.woocobe.course.ui.web.controller.response.CourseCommentDetailResponse
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import org.springframework.stereotype.Service

@Service
class CourseCommentQueryFacade(
    private val getAllCourseCommentUseCase: GetAllCourseCommentUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
) {
    fun getAllCourseComment(courseId: Long): List<CourseCommentDetailResponse> {
        val getAllCourseCommentResult = getAllCourseCommentUseCase.execute(GetAllCourseCommentInput(courseId))

        val userIds = getAllCourseCommentResult.courseComments.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds))

        return CourseCommentDetailResponse.listOf(
            courseComments = getAllCourseCommentResult.courseComments,
            users = getAllUserResult.users,
        )
    }
}

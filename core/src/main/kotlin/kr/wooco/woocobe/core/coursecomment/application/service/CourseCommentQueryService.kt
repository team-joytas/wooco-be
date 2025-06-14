package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.ReadAllCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.results.CourseCommentResult
import kr.wooco.woocobe.core.coursecomment.application.port.out.CourseCommentQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentQueryService(
    private val userQueryPort: UserQueryPort,
    private val courseCommentQueryPort: CourseCommentQueryPort,
) : ReadAllCourseCommentUseCase {
    @Transactional(readOnly = true)
    override fun readAllCourseComment(query: ReadAllCourseCommentUseCase.Query): List<CourseCommentResult> {
        val courseCommentViews = courseCommentQueryPort.getAllViewByCourseId(query.courseId)
        val writerIds = courseCommentViews.map { it.userId }.distinct()
        val writers = userQueryPort.getAllByUserIds(writerIds)
        return CourseCommentResult.listOf(courseCommentViews, writers)
    }
}

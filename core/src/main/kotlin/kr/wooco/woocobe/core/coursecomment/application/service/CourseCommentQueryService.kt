package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.ReadAllCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.results.CourseCommentResult
import kr.wooco.woocobe.core.coursecomment.application.port.out.LoadCourseCommentPersistencePort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentQueryService(
    private val userQueryPort: UserQueryPort,
    private val loadCourseCommentPersistencePort: LoadCourseCommentPersistencePort,
) : ReadAllCourseCommentUseCase {
    @Transactional(readOnly = true)
    override fun readAllCourseComment(query: ReadAllCourseCommentUseCase.Query): List<CourseCommentResult> {
        val courseComments = loadCourseCommentPersistencePort.getAllByCourseId(query.courseId)
        val writerIds = courseComments.map { it.userId }.distinct()
        val writers = userQueryPort.getAllByUserIds(writerIds)
        return CourseCommentResult.listOf(courseComments, writers)
    }
}

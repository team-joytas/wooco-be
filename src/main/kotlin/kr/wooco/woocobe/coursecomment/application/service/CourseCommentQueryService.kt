package kr.wooco.woocobe.coursecomment.application.service

import kr.wooco.woocobe.coursecomment.application.port.`in`.ReadAllCourseCommentUseCase
import kr.wooco.woocobe.coursecomment.application.port.`in`.results.CourseCommentResult
import kr.wooco.woocobe.coursecomment.application.port.out.LoadCourseCommentPersistencePort
import kr.wooco.woocobe.user.application.port.out.LoadUserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentQueryService(
    private val loadUserPersistencePort: LoadUserPersistencePort,
    private val loadCourseCommentPersistencePort: LoadCourseCommentPersistencePort,
) : ReadAllCourseCommentUseCase {
    @Transactional(readOnly = true)
    override fun readAllCourseComment(query: ReadAllCourseCommentUseCase.Query): List<CourseCommentResult> {
        val courseComments = loadCourseCommentPersistencePort.getAllByCourseId(query.courseId)
        val writerIds = courseComments.map { it.userId }.distinct()
        val writers = loadUserPersistencePort.getAllByUserIds(writerIds)
        return CourseCommentResult.listOf(courseComments, writers)
    }
}

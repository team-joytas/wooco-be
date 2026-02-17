package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.CreateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.DeleteCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.UpdateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.out.CourseCommentCommandPort
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentCommandService(
    private val courseQueryPort: CourseQueryPort,
    private val courseCommentCommandPort: CourseCommentCommandPort,
) : CreateCourseCommentUseCase,
    UpdateCourseCommentUseCase,
    DeleteCourseCommentUseCase {
    @Transactional
    override fun createCourseComment(command: CreateCourseCommentUseCase.Command): Long {
        val course = courseQueryPort.getActiveCommentTargetByCourseId(command.courseId)
        val courseComment = CourseComment.create(command.toCreateCommand(course.title, course.writerId)) {
            courseCommentCommandPort.saveCourseComment(it)
        }
        return courseComment.id
    }

    @Transactional
    override fun updateCourseComment(command: UpdateCourseCommentUseCase.Command) {
        val courseComment = courseCommentCommandPort.getByCourseCommentId(command.courseCommentId)
        courseCommentCommandPort.saveCourseComment(courseComment.updateContents(command.toUpdateContentsCommand()))
    }

    @Transactional
    override fun deleteCourseComment(command: DeleteCourseCommentUseCase.Command) {
        val courseComment = courseCommentCommandPort.getByCourseCommentId(command.courseCommentId)
        courseCommentCommandPort.saveCourseComment(courseComment.delete(command.toDeleteCommand()))
    }
}

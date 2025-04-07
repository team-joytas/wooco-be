package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.CreateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.DeleteCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.UpdateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.out.DeleteCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.LoadCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.SaveCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentCommandService(
    private val eventPublisher: ApplicationEventPublisher,
    private val courseQueryPort: CourseQueryPort,
    private val courseCommandPort: CourseCommandPort,
    private val loadCourseCommentPersistencePort: LoadCourseCommentPersistencePort,
    private val saveCourseCommentPersistencePort: SaveCourseCommentPersistencePort,
    private val deleteCourseCommentPersistencePort: DeleteCourseCommentPersistencePort,
) : CreateCourseCommentUseCase,
    UpdateCourseCommentUseCase,
    DeleteCourseCommentUseCase {
    // TODO: 로직 개선이 필요함 :: 로직간 강결합 문제
    @Transactional
    override fun createCourseComment(command: CreateCourseCommentUseCase.Command): Long {
        val course = courseQueryPort.getByCourseId(command.courseId)
        val courseComment = saveCourseCommentPersistencePort.saveCourseComment(
            CourseComment.create(
                userId = command.userId,
                courseId = command.courseId,
                contents = command.contents,
            ),
        )
        course.increaseComments()
        courseCommandPort.saveCourse(course)
        eventPublisher.publishEvent(CourseCommentCreateEvent.of(course, courseComment))
        return courseComment.id
    }

    @Transactional
    override fun updateCourseComment(command: UpdateCourseCommentUseCase.Command) {
        val courseComment = loadCourseCommentPersistencePort.getByCourseCommentId(command.courseCommentId)
        courseComment.updateContents(command.userId, command.contents)
        saveCourseCommentPersistencePort.saveCourseComment(courseComment)
    }

    @Transactional
    override fun deleteCourseComment(command: DeleteCourseCommentUseCase.Command) {
        val courseComment = loadCourseCommentPersistencePort.getByCourseCommentId(command.courseCommentId)
        courseComment.validateWriter(courseComment.userId)
        deleteCourseCommentPersistencePort.deleteByCourseCommentId(command.courseCommentId)
    }
}

package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.CreateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.DeleteCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.UpdateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.out.DeleteCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.LoadCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.SaveCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommentCommandService(
    private val loadCourseCommentPersistencePort: LoadCourseCommentPersistencePort,
    private val saveCourseCommentPersistencePort: SaveCourseCommentPersistencePort,
    private val deleteCourseCommentPersistencePort: DeleteCourseCommentPersistencePort,
) : CreateCourseCommentUseCase,
    UpdateCourseCommentUseCase,
    DeleteCourseCommentUseCase {
    // TODO: 댓글 추가시 이벤트 발생 --> 코스 댓글 수 증가
    @Transactional
    override fun createCourseComment(command: CreateCourseCommentUseCase.Command): Long {
        val courseComment = CourseComment.create(
            userId = command.userId,
            courseId = command.courseId,
            contents = command.contents,
        )
        return saveCourseCommentPersistencePort.saveCourseComment(courseComment).id
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

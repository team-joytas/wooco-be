package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.CourseComment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddCourseCommentInput(
    val userId: Long,
    val courseId: Long,
    val contents: String,
)

@Service
class AddCourseCommentUseCase(
    private val courseStorageGateway: CourseStorageGateway,
    private val courseCommentStorageGateway: CourseCommentStorageGateway,
) : UseCase<AddCourseCommentInput, Unit> {
    @Transactional
    override fun execute(input: AddCourseCommentInput) {
        val courseComment = CourseComment.register(
            userId = input.userId,
            courseId = input.courseId,
            contents = input.contents,
        )
        courseCommentStorageGateway.save(courseComment)

        // TODO
        // courseStorageGateway.increaseCommentsCounts(courseId = input.courseId)
        // 또는
        // 이벤트로 처리해버리기 :: 이벤트 처리전에 courseComment 전용 패키지를 하나 만들어야할듯
        val course = courseStorageGateway.getByCourseId(input.courseId)
        course.increaseComments()
        courseStorageGateway.save(course)
    }
}

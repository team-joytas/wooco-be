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

data class AddCourseCommentOutput(
    val commentId: Long,
)

// TODO: 코스 댓글 수 증가 로직 이벤트 처리
@Service
class AddCourseCommentUseCase(
    private val courseStorageGateway: CourseStorageGateway,
    private val courseCommentStorageGateway: CourseCommentStorageGateway,
) : UseCase<AddCourseCommentInput, AddCourseCommentOutput> {
    @Transactional
    override fun execute(input: AddCourseCommentInput): AddCourseCommentOutput {
        val courseComment = courseCommentStorageGateway.save(
            CourseComment.register(
                userId = input.userId,
                courseId = input.courseId,
                contents = input.contents,
            ),
        )

        val course = courseStorageGateway.getByCourseId(input.courseId)
        course.increaseComments()
        courseStorageGateway.save(course)

        return AddCourseCommentOutput(
            commentId = courseComment.id,
        )
    }
}

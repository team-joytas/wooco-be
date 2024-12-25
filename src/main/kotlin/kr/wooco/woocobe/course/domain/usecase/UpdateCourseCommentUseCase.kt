package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdateCourseCommentInput(
    val userId: Long,
    val commentId: Long,
    val contents: String,
)

@Service
class UpdateCourseCommentUseCase(
    private val courseCommentStorageGateway: CourseCommentStorageGateway,
) : UseCase<UpdateCourseCommentInput, Unit> {
    @Transactional
    override fun execute(input: UpdateCourseCommentInput) {
        val courseComment = courseCommentStorageGateway.getByCommentId(input.commentId)
            ?: throw RuntimeException()

        when {
            courseComment.isCommenter(input.userId).not() -> throw RuntimeException()
        }

        courseComment
            .update(contents = input.contents)
            .also(courseCommentStorageGateway::save)
    }
}

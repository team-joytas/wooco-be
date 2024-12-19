package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.user.domain.model.User
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
        val user = User.register(userId = input.userId)

        CourseComment
            .register(
                user = user,
                courseId = input.courseId,
                contents = input.contents,
            ).also(courseCommentStorageGateway::save)

        courseStorageGateway
            .getByCourseId(courseId = input.courseId)
            ?.run { increaseComments().also(courseStorageGateway::save) }
            ?: throw RuntimeException()
    }
}

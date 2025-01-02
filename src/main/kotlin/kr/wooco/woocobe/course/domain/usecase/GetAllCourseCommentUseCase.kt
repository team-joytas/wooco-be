package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import kr.wooco.woocobe.course.domain.model.CourseComment
import org.springframework.stereotype.Service

data class GetAllCourseCommentInput(
    val courseId: Long,
)

data class GetAllCourseCommentOutput(
    val courseComments: List<CourseComment>,
)

@Service
class GetAllCourseCommentUseCase(
    private val courseCommentStorageGateway: CourseCommentStorageGateway,
) : UseCase<GetAllCourseCommentInput, GetAllCourseCommentOutput> {
    override fun execute(input: GetAllCourseCommentInput): GetAllCourseCommentOutput {
        val courseComments = courseCommentStorageGateway.getAllByCourseId(input.courseId)

        return GetAllCourseCommentOutput(
            courseComments = courseComments,
        )
    }
}

package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import org.springframework.stereotype.Service

data class GetCourseInput(
    val userId: Long?,
    val courseId: Long,
)

data class GetCourseOutput(
    val course: Course,
)

@Service
class GetCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetCourseInput, GetCourseOutput> {
    override fun execute(input: GetCourseInput): GetCourseOutput {
        val course = courseStorageGateway.getByCourseId(input.courseId)
            ?: throw RuntimeException()

        course.increaseViews().also(courseStorageGateway::save)

        return GetCourseOutput(
            course = course,
        )
    }
}

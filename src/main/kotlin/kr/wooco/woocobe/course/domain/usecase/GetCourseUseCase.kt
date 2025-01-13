package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import org.springframework.stereotype.Service

data class GetCourseInput(
    val courseId: Long,
)

data class GetCourseOutput(
    val course: Course,
)

// TODO 이벤트 기반 고려 :: Query 작업에 Command 작업이 껴있다.
@Service
class GetCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetCourseInput, GetCourseOutput> {
    override fun execute(input: GetCourseInput): GetCourseOutput {
        val course = courseStorageGateway.getByCourseId(input.courseId)

        course.increaseViews()
        courseStorageGateway.save(course)

        return GetCourseOutput(
            course = course,
        )
    }
}

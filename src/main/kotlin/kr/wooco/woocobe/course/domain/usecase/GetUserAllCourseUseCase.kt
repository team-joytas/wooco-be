package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import org.springframework.stereotype.Service

data class GetUserAllCourseInput(
    val userId: Long,
    val sort: CourseSortCondition,
)

data class GetUserAllCourseOutput(
    val courses: List<Course>,
)

@Service
class GetUserAllCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetUserAllCourseInput, GetUserAllCourseOutput> {
    override fun execute(input: GetUserAllCourseInput): GetUserAllCourseOutput {
        val courses = courseStorageGateway.getAllByUserIdWithSort(input.userId, input.sort)

        return GetUserAllCourseOutput(
            courses = courses,
        )
    }
}

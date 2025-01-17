package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import org.springframework.stereotype.Service

data class GetAllUserCourseInput(
    val userId: Long,
    val sort: String,
)

data class GetAllUserCourseOutput(
    val courses: List<Course>,
)

@Service
class GetAllUserCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetAllUserCourseInput, GetAllUserCourseOutput> {
    override fun execute(input: GetAllUserCourseInput): GetAllUserCourseOutput {
        val sort = CourseSortCondition.from(input.sort)

        val courses = courseStorageGateway.getAllByUserIdWithSort(input.userId, sort)

        return GetAllUserCourseOutput(
            courses = courses,
        )
    }
}

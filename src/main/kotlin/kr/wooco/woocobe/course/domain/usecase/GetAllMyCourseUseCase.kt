package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import org.springframework.stereotype.Service

data class GetAllByCourseInput(
    val userId: Long,
    val sort: CourseSortCondition,
)

data class GetAllMyCourseOutput(
    val courses: List<Course>,
)

@Service
class GetAllMyCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetAllByCourseInput, GetAllMyCourseOutput> {
    override fun execute(input: GetAllByCourseInput): GetAllMyCourseOutput {
        val courses = courseStorageGateway.getAllByUserIdWithSort(input.userId, input.sort)

        return GetAllMyCourseOutput(
            courses = courses,
        )
    }
}

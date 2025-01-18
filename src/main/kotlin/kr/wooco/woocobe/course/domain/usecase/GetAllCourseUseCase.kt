package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import org.springframework.stereotype.Service

data class GetAllCourseInput(
    val primaryRegion: String?,
    val secondaryRegion: String?,
    val category: String?,
    val limit: Int?,
    val sort: String,
)

data class GetAllCourseOutput(
    val courses: List<Course>,
)

@Service
class GetAllCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<GetAllCourseInput, GetAllCourseOutput> {
    override fun execute(input: GetAllCourseInput): GetAllCourseOutput {
        val sort = CourseSortCondition.from(input.sort)

        val courses = courseStorageGateway.getAllByRegionAndCategoryWithSort(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
            category = input.category,
            limit = input.limit,
            sort = sort,
        )

        return GetAllCourseOutput(
            courses = courses,
        )
    }
}

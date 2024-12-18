package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import org.springframework.stereotype.Service

data class GetAllCourseInput(
    val primaryRegion: String,
    val secondaryRegion: String,
    val category: String,
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
        val sort = CourseSortCondition.from(value = input.sort)
        val region = CourseRegion.register(primaryRegion = input.primaryRegion, secondaryRegion = input.secondaryRegion)

        val courses = courseStorageGateway.getAllByRegionAndCategoryWithSort(
            region = region,
            category = input.category,
            sort = sort,
        )

        return GetAllCourseOutput(
            courses = courses,
        )
    }
}

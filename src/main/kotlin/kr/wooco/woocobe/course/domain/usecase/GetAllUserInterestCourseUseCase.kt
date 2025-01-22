package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class GetAllUserInterestCourseInput(
    val userId: Long,
    val limit: Int?,
)

data class GetAllUserInterestCourseOutput(
    val courses: List<Course>,
)

@Service
class GetAllUserInterestCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<GetAllUserInterestCourseInput, GetAllUserInterestCourseOutput> {
    @Transactional(readOnly = true)
    override fun execute(input: GetAllUserInterestCourseInput): GetAllUserInterestCourseOutput {
        val interestCourses = interestCourseStorageGateway.getAllByUserId(userId = input.userId, limit = input.limit)
        val courseIds = interestCourses.map { it.courseId }
        val courses = courseStorageGateway.getAllByCourseIds(courseIds)

        return GetAllUserInterestCourseOutput(
            courses = courses,
        )
    }
}

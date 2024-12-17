package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class GetAllMyInterestCourseInput(
    val userId: Long,
)

data class GetAllMyInterestCourseOutput(
    val courses: List<Course>,
)

@Service
class GetAllMyInterestCourseUseCase(
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<GetAllMyInterestCourseInput, GetAllMyInterestCourseOutput> {
    @Transactional(readOnly = true)
    override fun execute(input: GetAllMyInterestCourseInput): GetAllMyInterestCourseOutput {
        val interestCourses = interestCourseStorageGateway.getAllByUserId(userId = input.userId)

        return GetAllMyInterestCourseOutput(
            courses = interestCourses.map { it.course },
        )
    }
}

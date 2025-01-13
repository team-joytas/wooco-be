package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import org.springframework.stereotype.Service

data class CheckInterestCourseInput(
    val userId: Long,
    val courseId: Long,
)

data class CheckInterestCourseOutput(
    val isInterested: Boolean,
)

@Service
class CheckInterestCourseUseCase(
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<CheckInterestCourseInput, CheckInterestCourseOutput> {
    override fun execute(input: CheckInterestCourseInput): CheckInterestCourseOutput {
        val isInterested = interestCourseStorageGateway.existsByCourseIdAndUserId(input.courseId, input.userId)

        return CheckInterestCourseOutput(
            isInterested = isInterested,
        )
    }
}

package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import org.springframework.stereotype.Service

data class GetAllInterestCourseIdInput(
    val userId: Long,
    val courseIds: List<Long>,
)

data class GetAllInterestCourseIdOutput(
    val interestCourseIds: List<Long>,
)

@Service
class GetAllInterestCourseIdUseCase(
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<GetAllInterestCourseIdInput, GetAllInterestCourseIdOutput> {
    override fun execute(input: GetAllInterestCourseIdInput): GetAllInterestCourseIdOutput {
        val interestCourseIds =
            interestCourseStorageGateway.getInterestCourseIdsByUserIdAndCourseIds(input.userId, input.courseIds)

        return GetAllInterestCourseIdOutput(
            interestCourseIds = interestCourseIds,
        )
    }
}

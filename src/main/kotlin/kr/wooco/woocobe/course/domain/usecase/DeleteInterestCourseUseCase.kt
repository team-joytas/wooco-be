package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class DeleteInterestCourseInput(
    val userId: Long,
    val courseId: Long,
)

@Service
class DeleteInterestCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<DeleteInterestCourseInput, Unit> {
    @Transactional
    override fun execute(input: DeleteInterestCourseInput) {
        val interestCourse = interestCourseStorageGateway.getByCourseIdAndUserId(input.courseId, input.userId)
            ?: throw RuntimeException()

        val course = courseStorageGateway.getByCourseId(interestCourse.courseId)
        course.decreaseInterests()
        courseStorageGateway.save(course)
    }
}

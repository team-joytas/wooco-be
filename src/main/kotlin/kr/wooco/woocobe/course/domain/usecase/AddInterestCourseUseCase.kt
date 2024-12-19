package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.InterestCourse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddInterestCourseInput(
    val userId: Long,
    val courseId: Long,
)

@Service
class AddInterestCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
    private val interestCourseStorageGateway: InterestCourseStorageGateway,
) : UseCase<AddInterestCourseInput, Unit> {
    @Transactional
    override fun execute(input: AddInterestCourseInput) {
        interestCourseStorageGateway
            .existsByCourseIdAndUserId(courseId = input.courseId, userId = input.userId)
            .takeIf { it }
            ?.let { throw RuntimeException() }

        val course = courseStorageGateway.getByCourseId(input.courseId)
            ?: throw RuntimeException()

        InterestCourse
            .register(userId = input.userId, course = course)
            .also(interestCourseStorageGateway::save)

        course
            .increaseInterests()
            .also(courseStorageGateway::save)
    }
}

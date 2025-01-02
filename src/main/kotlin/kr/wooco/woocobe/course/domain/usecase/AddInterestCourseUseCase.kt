package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
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
        if (interestCourseStorageGateway.existsByCourseIdAndUserId(courseId = input.courseId, userId = input.userId)) {
            throw RuntimeException("already interest course ${input.courseId}")
        }

        val interestCourse = InterestCourse.register(userId = input.userId, courseId = input.courseId)
        interestCourseStorageGateway.save(interestCourse)

        // TODO: 증가 로직 수정
        val course = courseStorageGateway.getByCourseId(input.courseId)
            ?: throw RuntimeException()
        course.increaseInterests()
        courseStorageGateway.save(course)
    }
}

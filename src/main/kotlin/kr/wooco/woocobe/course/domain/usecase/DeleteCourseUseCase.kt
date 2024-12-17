package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class DeleteCourseInput(
    val userId: Long,
    val courseId: Long,
)

@Service
class DeleteCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<DeleteCourseInput, Unit> {
    @Transactional
    override fun execute(input: DeleteCourseInput) {
        val course = courseStorageGateway
            .getByCourseId(courseId = input.courseId)
            ?: throw RuntimeException()

        when {
            course.isWriter(input.userId).not() -> throw RuntimeException()
        }

        courseStorageGateway.deleteByCourseId(courseId = course.id)
    }
}
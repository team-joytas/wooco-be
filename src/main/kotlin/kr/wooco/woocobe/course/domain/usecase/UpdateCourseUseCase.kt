package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdateCourseInput(
    val userId: Long,
    val courseId: Long,
    val name: String,
    val contents: String,
    val categories: List<String>,
)

@Service
class UpdateCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<UpdateCourseInput, Unit> {
    @Transactional
    override fun execute(input: UpdateCourseInput) {
        val course = courseStorageGateway.getByCourseId(courseId = input.courseId)
            ?: throw RuntimeException()

        when {
            course.isWriter(input.userId).not() -> throw RuntimeException()
        }

        course
            .update(
                name = input.name,
                contents = input.contents,
                categories = input.categories,
            ).also(courseStorageGateway::save)
    }
}

package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

data class UpdateCourseInput(
    val userId: Long,
    val courseId: Long,
    val title: String,
    val contents: String,
    val categories: List<String>,
    val placeIds: List<Long>,
    val visitDate: LocalDate,
)

@Service
class UpdateCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<UpdateCourseInput, Unit> {
    @Transactional
    override fun execute(input: UpdateCourseInput) {
        val course = courseStorageGateway.getByCourseId(courseId = input.courseId)
        course.isValidWriter(input.userId)

        course.update(
            title = input.title,
            contents = input.contents,
            categories = input.categories,
            placeIds = input.placeIds,
            visitDate = input.visitDate,
        )
        courseStorageGateway.save(course)
    }
}

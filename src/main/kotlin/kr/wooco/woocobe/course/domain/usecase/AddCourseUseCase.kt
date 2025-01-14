package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddCourseUseCaseInput(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val category: List<String>,
    val name: String,
    val contents: String,
    val placeIds: List<Long>,
)

data class AddCourseUseCaseOutput(
    val courseId: Long,
)

@Service
class AddCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<AddCourseUseCaseInput, AddCourseUseCaseOutput> {
    @Transactional
    override fun execute(input: AddCourseUseCaseInput): AddCourseUseCaseOutput {
        val courseRegion = CourseRegion.register(input.primaryRegion, input.secondaryRegion)

        val course = courseStorageGateway.save(
            Course.register(
                userId = input.userId,
                region = courseRegion,
                categories = input.category,
                name = input.name,
                contents = input.contents,
                placeIds = input.placeIds,
            ),
        )

        return AddCourseUseCaseOutput(
            courseId = course.id,
        )
    }
}

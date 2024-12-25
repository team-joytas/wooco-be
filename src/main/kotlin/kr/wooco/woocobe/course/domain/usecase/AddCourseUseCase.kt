package kr.wooco.woocobe.course.domain.usecase

import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.user.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class AddCourseUseCaseInput(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val category: List<String>,
    val name: String,
    val contents: String,
)

@Service
class AddCourseUseCase(
    private val courseStorageGateway: CourseStorageGateway,
) : UseCase<AddCourseUseCaseInput, Unit> {
    @Transactional
    override fun execute(input: AddCourseUseCaseInput) {
        val user = User.register(userId = input.userId)

        val courseRegion = CourseRegion.register(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
        )

        Course
            .register(
                user = user,
                region = courseRegion,
                categories = input.category,
                name = input.name,
                contents = input.contents,
            ).also(courseStorageGateway::save)
    }
}

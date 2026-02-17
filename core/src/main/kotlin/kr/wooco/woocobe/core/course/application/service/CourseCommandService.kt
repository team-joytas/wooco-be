package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.CreateCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.DeleteCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.UpdateCourseInfoUseCase
import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import kr.wooco.woocobe.core.course.domain.entity.Course
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommandService(
    private val courseCommandPort: CourseCommandPort,
) : CreateCourseUseCase,
    UpdateCourseInfoUseCase,
    DeleteCourseUseCase {
    @Transactional
    override fun createCourse(command: CreateCourseUseCase.Command): Long {
        val course = Course.create(command.toCreateCommand()) { course ->
            courseCommandPort.saveCourse(course)
        }
        return course.id
    }

    @Transactional
    override fun updateCourseInfo(command: UpdateCourseInfoUseCase.Command) {
        val course = courseCommandPort.getByCourseId(command.courseId)
        val updatedCourse = course.updateInfo(command.toUpdateInfoCommand())
        courseCommandPort.saveCourse(updatedCourse)
    }

    @Transactional
    override fun deleteCourse(command: DeleteCourseUseCase.Command) {
        val course = courseCommandPort.getByCourseId(command.courseId)
        val deletedCourse = course.delete(command.toDeleteCommand())
        courseCommandPort.saveCourse(deletedCourse)
    }
}

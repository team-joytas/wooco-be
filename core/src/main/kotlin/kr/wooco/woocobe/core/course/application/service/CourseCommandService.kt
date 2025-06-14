package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.CreateCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.CreateInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.DeleteCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.DeleteInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.UpdateCourseInfoUseCase
import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeCommandPort
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.entity.CourseLike
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommandService(
    private val courseCommandPort: CourseCommandPort,
    private val courseLikeCommandPort: CourseLikeCommandPort,
) : CreateCourseUseCase,
    UpdateCourseInfoUseCase,
    DeleteCourseUseCase,
    CreateInterestCourseUseCase,
    DeleteInterestCourseUseCase {
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

    // 코스 이외에 다른 좋아요 추가시 패키지 분리 필요할 듯
    @Transactional
    override fun createInterestCourse(command: CreateInterestCourseUseCase.Command) {
        val like = courseLikeCommandPort.getOrNullByUserIdAndCourseId(command.userId, command.courseId)

        if (like != null) {
            courseLikeCommandPort.saveLikeCourse(like.active())
        } else {
            CourseLike.create(command.toCreateCommand()) {
                courseLikeCommandPort.saveLikeCourse(it)
            }
        }
    }

    @Transactional
    override fun deleteInterestCourse(command: DeleteInterestCourseUseCase.Command) {
        val like = courseLikeCommandPort.getByUserIdAndCourseId(command.userId, command.courseId)
        courseLikeCommandPort.saveLikeCourse(like.delete())
    }
}

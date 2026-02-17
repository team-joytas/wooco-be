package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.CreateInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.`in`.DeleteInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.domain.entity.CourseLike
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseLikeCommandService(
    private val courseQueryPort: CourseQueryPort,
    private val courseLikeCommandPort: CourseLikeCommandPort,
) : CreateInterestCourseUseCase,
    DeleteInterestCourseUseCase {
    @Transactional
    override fun createInterestCourse(command: CreateInterestCourseUseCase.Command) {
        validateActiveCourse(command.courseId)
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

    private fun validateActiveCourse(courseId: Long) {
        if (courseQueryPort.existsActiveByCourseId(courseId).not()) {
            throw NotExistsCourseException
        }
    }
}

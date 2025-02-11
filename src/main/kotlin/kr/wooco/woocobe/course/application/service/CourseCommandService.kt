package kr.wooco.woocobe.course.application.service

import kr.wooco.woocobe.course.application.port.`in`.CreateCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.CreateInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.DeleteCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.DeleteInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.UpdateCourseUseCase
import kr.wooco.woocobe.course.application.port.out.DeleteCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.DeleteInterestCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.LoadCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.SaveCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.SaveInterestCoursePersistencePort
import kr.wooco.woocobe.course.domain.entity.Course
import kr.wooco.woocobe.course.domain.entity.InterestCourse
import kr.wooco.woocobe.course.domain.exception.AlreadyLikedCourseException
import kr.wooco.woocobe.course.domain.vo.CourseRegion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class CourseCommandService(
    private val loadCoursePersistencePort: LoadCoursePersistencePort,
    private val saveCoursePersistencePort: SaveCoursePersistencePort,
    private val deleteCoursePersistencePort: DeleteCoursePersistencePort,
    private val loadInterestCoursePersistencePort: LoadInterestCoursePersistencePort,
    private val saveInterestCoursePersistencePort: SaveInterestCoursePersistencePort,
    private val deleteInterestCoursePersistencePort: DeleteInterestCoursePersistencePort,
) : CreateCourseUseCase,
    UpdateCourseUseCase,
    DeleteCourseUseCase,
    CreateInterestCourseUseCase,
    DeleteInterestCourseUseCase {
    @Transactional
    override fun createCourse(command: CreateCourseUseCase.Command): Long {
        val region = CourseRegion(
            primaryRegion = command.primaryRegion,
            secondaryRegion = command.secondaryRegion,
        )
        val course = Course.create(
            userId = command.userId,
            region = region,
            categories = command.categories,
            placeIds = command.placeIds,
            title = command.title,
            contents = command.contents,
            visitDate = command.visitDate,
        )
        return saveCoursePersistencePort.saveCourse(course).id
    }

    @Transactional
    override fun updateCourse(command: UpdateCourseUseCase.Command) {
        val course = loadCoursePersistencePort.getByCourseId(command.courseId)
        course.update(
            userId = command.userId,
            title = command.title,
            categories = command.categories,
            contents = command.contents,
            placeIds = command.placeIds,
            visitDate = command.visitDate,
        )
        saveCoursePersistencePort.saveCourse(course)
    }

    @Transactional
    override fun deleteCourse(command: DeleteCourseUseCase.Command) {
        val course = loadCoursePersistencePort.getByCourseId(command.courseId)
        course.isValidWriter(command.userId)
        deleteCoursePersistencePort.deleteByCourseId(command.courseId)
    }

    @Transactional
    override fun createInterestCourse(command: CreateInterestCourseUseCase.Command) {
        if (loadInterestCoursePersistencePort.existsByUserIdAndCourseId(command.userId, command.courseId)) {
            throw AlreadyLikedCourseException
        }
        val interestCourse = InterestCourse.create(command.userId, command.courseId)
        saveInterestCoursePersistencePort.saveInterestCourse(interestCourse)
        val course = loadCoursePersistencePort.getByCourseId(command.courseId)
        course.increaseInterests()
        saveCoursePersistencePort.saveCourse(course)
    }

    @Transactional
    override fun deleteInterestCourse(command: DeleteInterestCourseUseCase.Command) {
        val interestCourse = loadInterestCoursePersistencePort.getByUserIdAndCourseId(command.courseId, command.userId)
        deleteInterestCoursePersistencePort.deleteByInterestCourseId(interestCourse.id)
        val course = loadCoursePersistencePort.getByCourseId(command.courseId)
        course.decreaseInterests()
        saveCoursePersistencePort.saveCourse(course)
    }
}

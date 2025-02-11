package kr.wooco.woocobe.course.adapter.out.persistence

import kr.wooco.woocobe.course.adapter.out.persistence.repository.InterestCourseJpaRepository
import kr.wooco.woocobe.course.application.port.out.DeleteInterestCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.course.application.port.out.SaveInterestCoursePersistencePort
import kr.wooco.woocobe.course.domain.entity.InterestCourse
import kr.wooco.woocobe.course.domain.exception.NotExistsInterestCourseException
import org.springframework.stereotype.Component

@Component
internal class InterestCoursePersistenceAdapter(
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
    private val interestCoursePersistenceMapper: InterestCoursePersistenceMapper,
) : LoadInterestCoursePersistencePort,
    SaveInterestCoursePersistencePort,
    DeleteInterestCoursePersistencePort {
    override fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourse {
        val interestCourseEntity = interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)
            ?: throw NotExistsInterestCourseException
        return interestCoursePersistenceMapper.toDomain(interestCourseEntity)
    }

    override fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserId(courseId, userId)

    override fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long> = interestCourseJpaRepository.findCourseIdsByUserIdAndCourseIds(userId, courseIds)

    override fun saveInterestCourse(interestCourse: InterestCourse): InterestCourse {
        val interestCourseEntity = interestCoursePersistenceMapper.toEntity(interestCourse)
        interestCourseJpaRepository.save(interestCourseEntity)
        return interestCoursePersistenceMapper.toDomain(interestCourseEntity)
    }

    override fun deleteByInterestCourseId(interestCourseId: Long) {
        interestCourseJpaRepository.deleteById(interestCourseId)
    }
}

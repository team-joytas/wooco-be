package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.DeleteInterestCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.SaveInterestCoursePersistencePort
import kr.wooco.woocobe.core.course.domain.entity.InterestCourse
import kr.wooco.woocobe.core.course.domain.exception.NotExistsInterestCourseException
import kr.wooco.woocobe.mysql.course.repository.InterestCourseJpaRepository
import org.springframework.stereotype.Component

@Component
internal class InterestCoursePersistenceAdapter(
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
) : LoadInterestCoursePersistencePort,
    SaveInterestCoursePersistencePort,
    DeleteInterestCoursePersistencePort {
    override fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourse {
        val interestCourseJpaEntity = interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)
            ?: throw NotExistsInterestCourseException
        return InterestCoursePersistenceMapper.toDomainEntity(interestCourseJpaEntity)
    }

    override fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserId(courseId, userId)

    override fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long> = interestCourseJpaRepository.findCourseIdsByUserIdAndCourseIds(userId, courseIds)

    override fun countByUserId(userId: Long): Long = interestCourseJpaRepository.countByUserId(userId)

    override fun saveInterestCourse(interestCourse: InterestCourse): InterestCourse {
        val interestCourseJpaEntity = InterestCoursePersistenceMapper.toJpaEntity(interestCourse)
        interestCourseJpaRepository.save(interestCourseJpaEntity)
        return InterestCoursePersistenceMapper.toDomainEntity(interestCourseJpaEntity)
    }

    override fun deleteByInterestCourseId(interestCourseId: Long) {
        interestCourseJpaRepository.deleteById(interestCourseId)
    }
}

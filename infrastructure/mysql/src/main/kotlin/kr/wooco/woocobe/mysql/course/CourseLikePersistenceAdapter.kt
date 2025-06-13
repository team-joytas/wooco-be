package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.LikeCourseCommandPort
import kr.wooco.woocobe.core.course.application.port.out.LikeCourseQueryPort
import kr.wooco.woocobe.core.course.domain.entity.LikeCourse
import kr.wooco.woocobe.core.course.domain.exception.NotExistsInterestCourseException
import kr.wooco.woocobe.mysql.course.repository.InterestCourseJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class InterestCoursePersistenceAdapter(
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
) : LikeCourseQueryPort,
    LikeCourseCommandPort {
    override fun countByUserId(userId: Long): Long = interestCourseJpaRepository.countByUserIdAndActive(userId)

    override fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long> = interestCourseJpaRepository.findCourseIdsByUserIdAndCourseIdsAndActive(userId, courseIds)

    override fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserIdAndActive(courseId, userId)

    override fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): LikeCourse {
        val interestCourseJpaEntity = interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)
            ?: throw NotExistsInterestCourseException
        return InterestCoursePersistenceMapper.toDomainEntity(interestCourseJpaEntity)
    }

    override fun getOrNullByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): LikeCourse? =
        interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)?.let {
            InterestCoursePersistenceMapper.toDomainEntity(it)
        }

    @Transactional
    override fun saveLikeCourse(likeCourse: LikeCourse): Long {
        val interestCourseJpaEntity = InterestCoursePersistenceMapper.toJpaEntity(likeCourse)
        return interestCourseJpaRepository.save(interestCourseJpaEntity).id
    }

//    override fun saveInterestCourse(interestCourse: InterestCourse): InterestCourse {
//        val interestCourseJpaEntity = InterestCoursePersistenceMapper.toJpaEntity(interestCourse)
//        interestCourseJpaRepository.save(interestCourseJpaEntity)
//        return InterestCoursePersistenceMapper.toDomainEntity(interestCourseJpaEntity)
//    }

//    override fun deleteByInterestCourseId(interestCourseId: Long) {
//        interestCourseJpaRepository.deleteById(interestCourseId)
//    }
}

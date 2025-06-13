package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.CourseLikeCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeQueryPort
import kr.wooco.woocobe.core.course.domain.entity.CourseLike
import kr.wooco.woocobe.core.course.domain.exception.NotExistsInterestCourseException
import kr.wooco.woocobe.mysql.course.repository.CourseLikeJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class CourseLikePersistenceAdapter(
    private val courseLikeJpaRepository: CourseLikeJpaRepository,
) : CourseLikeQueryPort,
    CourseLikeCommandPort {
    override fun countByUserId(userId: Long): Long = courseLikeJpaRepository.countByUserIdAndActive(userId)

    override fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long> = courseLikeJpaRepository.findCourseIdsByUserIdAndCourseIdsAndActive(userId, courseIds)

    override fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean = courseLikeJpaRepository.existsByCourseIdAndUserIdAndActive(courseId, userId)

    override fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): CourseLike {
        val interestCourseJpaEntity = courseLikeJpaRepository.findByUserIdAndCourseId(userId, courseId)
            ?: throw NotExistsInterestCourseException
        return CourseLikePersistenceMapper.toDomainEntity(interestCourseJpaEntity)
    }

    override fun getOrNullByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): CourseLike? =
        courseLikeJpaRepository.findByUserIdAndCourseId(userId, courseId)?.let {
            CourseLikePersistenceMapper.toDomainEntity(it)
        }

    @Transactional
    override fun saveLikeCourse(courseLike: CourseLike): Long {
        val interestCourseJpaEntity = CourseLikePersistenceMapper.toJpaEntity(courseLike)
        return courseLikeJpaRepository.save(interestCourseJpaEntity).id
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

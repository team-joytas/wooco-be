package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.InterestCourse
import kr.wooco.woocobe.course.infrastructure.storage.CourseCategoryJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.CourseJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.InterestCourseEntity
import kr.wooco.woocobe.course.infrastructure.storage.InterestCourseJpaRepository
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
@Suppress("Duplicates") // TODO: 매퍼 클래스로 중복 제거
class JpaInterestCourseStorageGateway(
    private val userJpaRepository: UserJpaRepository,
    private val courseJpaRepository: CourseJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
) : InterestCourseStorageGateway {
    override fun save(interestCourse: InterestCourse): InterestCourse {
        interestCourseJpaRepository.save(InterestCourseEntity.from(interestCourse))
        return interestCourse
    }

    override fun getByCourseIdAndUserId(
        userId: Long,
        courseId: Long,
    ): InterestCourse? =
        interestCourseJpaRepository
            .findByUserIdAndCourseId(userId, courseId)
            ?.let {
                val user = userJpaRepository.findByIdOrNull(it.userId)!!.toDomain()
                it.toDomain(course = courseJpaRepository.findByIdOrNull(courseId)!!.toDomain(user = user))
            }

    override fun getAllByUserId(userId: Long): List<InterestCourse> {
        val interestCourseEntities = interestCourseJpaRepository.findAllByUserId(userId = userId)
        val courses = courseJpaRepository
            .findAllById(interestCourseEntities.map { it.courseId })
            .run {
                val userEntities = userJpaRepository.findAllById(map { it.userId })
                val categoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(map { it.id!! })
                map { courseEntity ->
                    courseEntity.toDomain(
                        user = userEntities.find { courseEntity.userId == it.id }!!.toDomain(),
                        courseCategory = categoryEntities
                            .filter { courseEntity.userId == it.courseId }
                            .map { it.toDomain() },
                    )
                }
            }
        return interestCourseEntities.map { interestCourseEntity ->
            interestCourseEntity.toDomain(
                course = courses.find { it.id == interestCourseEntity.courseId }!!,
            )
        }
    }

    override fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserId(courseId = courseId, userId = userId)

    override fun deleteByInterestCourseId(interestCourseId: Long) = interestCourseJpaRepository.deleteById(interestCourseId)
}

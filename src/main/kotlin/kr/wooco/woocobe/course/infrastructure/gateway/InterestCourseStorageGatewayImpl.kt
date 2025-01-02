package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.InterestCourse
import kr.wooco.woocobe.course.infrastructure.storage.entity.InterestCourseJpaEntity
import kr.wooco.woocobe.course.infrastructure.storage.repository.InterestCourseJpaRepository
import org.springframework.stereotype.Component

@Component
class InterestCourseStorageGatewayImpl(
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
) : InterestCourseStorageGateway {
    override fun save(interestCourse: InterestCourse): InterestCourse =
        interestCourseJpaRepository.save(InterestCourseJpaEntity.from(interestCourse)).toDomain()

    override fun getByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): InterestCourse? = interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)?.toDomain()

    override fun getAllByUserId(userId: Long): List<InterestCourse> =
        interestCourseJpaRepository.findAllByUserId(userId = userId).map { it.toDomain() }

    override fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserId(courseId = courseId, userId = userId)

    override fun deleteByInterestCourseId(interestCourseId: Long) = interestCourseJpaRepository.deleteById(interestCourseId)
}

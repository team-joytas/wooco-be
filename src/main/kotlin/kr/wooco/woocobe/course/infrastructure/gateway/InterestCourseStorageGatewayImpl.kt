package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.exception.NotExistsInterestCourseException
import kr.wooco.woocobe.course.domain.gateway.InterestCourseStorageGateway
import kr.wooco.woocobe.course.domain.model.InterestCourse
import kr.wooco.woocobe.course.infrastructure.storage.InterestCourseStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.repository.InterestCourseJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
internal class InterestCourseStorageGatewayImpl(
    private val interestCourseJpaRepository: InterestCourseJpaRepository,
    private val interestCourseStorageMapper: InterestCourseStorageMapper,
) : InterestCourseStorageGateway {
    override fun save(interestCourse: InterestCourse): InterestCourse {
        val interestCourseEntity = interestCourseStorageMapper.toEntity(interestCourse)
        interestCourseJpaRepository.save(interestCourseEntity)
        return interestCourseStorageMapper.toDomain(interestCourseEntity)
    }

    override fun getByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): InterestCourse {
        val interestCourseEntity = interestCourseJpaRepository.findByUserIdAndCourseId(userId, courseId)
            ?: throw NotExistsInterestCourseException
        return interestCourseStorageMapper.toDomain(interestCourseEntity)
    }

    override fun getAllByUserId(
        userId: Long,
        limit: Int?,
    ): List<InterestCourse> {
        val pageable = limit?.let { PageRequest.of(0, it) } ?: Pageable.unpaged()
        val interestCourseEntities = interestCourseJpaRepository.findAllByUserId(userId = userId, pageable = pageable)
        return interestCourseEntities.map { interestCourseStorageMapper.toDomain(it) }
    }

    override fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean = interestCourseJpaRepository.existsByCourseIdAndUserId(courseId = courseId, userId = userId)

    override fun getInterestCourseIdsByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long> = interestCourseJpaRepository.findCourseIdsByUserIdAndCourseIds(userId, courseIds)

    override fun deleteByInterestCourseId(interestCourseId: Long) = interestCourseJpaRepository.deleteById(interestCourseId)
}

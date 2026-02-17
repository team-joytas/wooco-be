package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.CourseMetaProjectionPort
import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import kr.wooco.woocobe.mysql.course.repository.CourseJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CourseMetaJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
internal class CourseMetaPersistenceAdapter(
    private val courseJpaRepository: CourseJpaRepository,
    private val courseMetaJpaRepository: CourseMetaJpaRepository,
) : CourseMetaProjectionPort {
    override fun getByCourseId(courseId: Long): CourseMetaProjection =
        courseMetaJpaRepository
            .findByIdOrNull(courseId)
            ?.let(CourseMetaPersistenceMapper::toProjection)
            ?: throw NotExistsCourseException

    @Transactional
    override fun save(courseMetaProjection: CourseMetaProjection) {
        courseMetaJpaRepository.save(CourseMetaPersistenceMapper.toJpaEntity(courseMetaProjection))
    }

    override fun getCreatedAtByCourseId(courseId: Long): LocalDateTime =
        courseJpaRepository.findCreatedAtByCourseId(courseId)
            ?: throw NotExistsCourseException
}

package kr.wooco.woocobe.mysql.coursecomment

import kr.wooco.woocobe.core.coursecomment.application.port.out.CourseCommentCommandPort
import kr.wooco.woocobe.core.coursecomment.application.port.out.CourseCommentQueryPort
import kr.wooco.woocobe.core.coursecomment.application.port.out.dto.CourseCommentView
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.core.coursecomment.domain.exception.NotExistsCommentException
import kr.wooco.woocobe.mysql.coursecomment.repository.CourseCommentJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class CourseCommentPersistenceAdapter(
    private val courseCommentJpaRepository: CourseCommentJpaRepository,
    private val courseCommentPersistenceMapper: CourseCommentPersistenceMapper,
) : CourseCommentQueryPort,
    CourseCommentCommandPort {
    override fun getAllViewByCourseId(courseId: Long): List<CourseCommentView> {
        val courseCommentJpaEntities = courseCommentJpaRepository.findAllByCourseId(courseId)
        return courseCommentJpaEntities.map { courseCommentPersistenceMapper.toReadModel(it) }
    }

    override fun getByCourseCommentId(courseCommentId: Long): CourseComment {
        val courseCommentJpaEntity = courseCommentJpaRepository.findByIdOrNull(courseCommentId)
            ?: throw NotExistsCommentException
        return courseCommentPersistenceMapper.toDomain(courseCommentJpaEntity)
    }

    @Transactional
    override fun saveCourseComment(courseComment: CourseComment): Long {
        val courseCommentJpaEntity =
            courseCommentJpaRepository.save(courseCommentPersistenceMapper.toEntity(courseComment))
        return courseCommentJpaEntity.id
    }
}

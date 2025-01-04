package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.course.infrastructure.storage.CourseCommentStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseCommentJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class CourseCommentStorageGatewayImpl(
    private val courseCommentJpaRepository: CourseCommentJpaRepository,
    private val courseCommentStorageMapper: CourseCommentStorageMapper,
) : CourseCommentStorageGateway {
    override fun save(courseComment: CourseComment): CourseComment {
        val courseCommentEntity = courseCommentStorageMapper.toEntity(courseComment)
        courseCommentJpaRepository.save(courseCommentEntity)
        return courseCommentStorageMapper.toDomain(courseCommentEntity)
    }

    override fun getByCommentId(commentId: Long): CourseComment {
        val courseCommentEntity = courseCommentJpaRepository.findByIdOrNull(commentId)
            ?: throw RuntimeException()
        return courseCommentStorageMapper.toDomain(courseCommentEntity)
    }

    override fun getAllByCourseId(courseId: Long): List<CourseComment> {
        val courseCommentEntities = courseCommentJpaRepository.findAllByCourseId(courseId)
        return courseCommentEntities.map { courseCommentStorageMapper.toDomain(it) }
    }

    override fun deleteByCommentId(commentId: Long) = courseCommentJpaRepository.deleteById(commentId)
}

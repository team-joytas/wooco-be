package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.CourseCommentStorageGateway
import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.course.infrastructure.storage.CourseCommentEntity
import kr.wooco.woocobe.course.infrastructure.storage.CourseCommentJpaRepository
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaCourseCommentStorageGateway(
    private val userJpaRepository: UserJpaRepository,
    private val courseCommentJpaRepository: CourseCommentJpaRepository,
) : CourseCommentStorageGateway {
    override fun save(courseComment: CourseComment): CourseComment {
        courseCommentJpaRepository.save(CourseCommentEntity.from(courseComment))
        return courseComment
    }

    override fun getByCommentId(commentId: Long): CourseComment? =
        courseCommentJpaRepository.findByIdOrNull(commentId)?.let {
            it.toDomain(user = userJpaRepository.findByIdOrNull(it.userId)!!.toDomain())
        }

    override fun getAllByCourseId(courseId: Long): List<CourseComment> {
        val courseEntities = courseCommentJpaRepository.findAllByCourseId(courseId)
        val userEntities = userJpaRepository.findAllById(courseEntities.map { it.userId })
        return courseEntities.map { courseEntity ->
            courseEntity.toDomain(
                user = userEntities.find { it.id == courseEntity.userId }!!.toDomain(),
            )
        }
    }

    override fun deleteByCommentId(commentId: Long) = courseCommentJpaRepository.deleteById(commentId)
}

package kr.wooco.woocobe.course.infrastructure.storage

import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCommentJpaEntity
import org.springframework.stereotype.Component

@Component
class CourseCommentStorageMapper {
    fun toDomain(courseCommentJpaEntity: CourseCommentJpaEntity): CourseComment =
        CourseComment(
            id = courseCommentJpaEntity.id!!,
            userId = courseCommentJpaEntity.userId,
            courseId = courseCommentJpaEntity.courseId,
            contents = courseCommentJpaEntity.contents,
            commentDateTime = courseCommentJpaEntity.createdAt,
        )

    fun toEntity(courseComment: CourseComment): CourseCommentJpaEntity =
        CourseCommentJpaEntity(
            id = courseComment.id,
            userId = courseComment.userId,
            courseId = courseComment.courseId,
            contents = courseComment.contents,
        )
}

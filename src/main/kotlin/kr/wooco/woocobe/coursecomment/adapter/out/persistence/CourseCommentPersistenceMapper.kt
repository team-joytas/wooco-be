package kr.wooco.woocobe.coursecomment.adapter.out.persistence

import kr.wooco.woocobe.coursecomment.adapter.out.persistence.entity.CourseCommentJpaEntity
import kr.wooco.woocobe.coursecomment.domain.entity.CourseComment
import org.springframework.stereotype.Component

@Component
internal class CourseCommentPersistenceMapper {
    fun toDomain(courseCommentJpaEntity: CourseCommentJpaEntity): CourseComment =
        CourseComment(
            id = courseCommentJpaEntity.id,
            userId = courseCommentJpaEntity.userId,
            courseId = courseCommentJpaEntity.courseId,
            contents = CourseComment.Contents(
                value = courseCommentJpaEntity.contents,
            ),
            createdAt = courseCommentJpaEntity.createdAt,
        )

    fun toEntity(courseComment: CourseComment): CourseCommentJpaEntity =
        CourseCommentJpaEntity(
            id = courseComment.id,
            userId = courseComment.userId,
            courseId = courseComment.courseId,
            contents = courseComment.contents.value,
        )
}

package kr.wooco.woocobe.mysql.coursecomment

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.mysql.coursecomment.entity.CourseCommentJpaEntity
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

package kr.wooco.woocobe.mysql.coursecomment

import kr.wooco.woocobe.core.coursecomment.application.port.out.dto.CourseCommentView
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
            status = CourseComment.Status.valueOf(courseCommentJpaEntity.status),
        )

    fun toEntity(courseComment: CourseComment): CourseCommentJpaEntity =
        CourseCommentJpaEntity(
            id = courseComment.id,
            userId = courseComment.userId,
            courseId = courseComment.courseId,
            contents = courseComment.contents.value,
            status = courseComment.status.name,
        )

    fun toReadModel(it: CourseCommentJpaEntity): CourseCommentView =
        CourseCommentView(
            id = it.id,
            userId = it.userId,
            courseId = it.courseId,
            contents = it.contents,
            createdAt = it.createdAt,
        )
}

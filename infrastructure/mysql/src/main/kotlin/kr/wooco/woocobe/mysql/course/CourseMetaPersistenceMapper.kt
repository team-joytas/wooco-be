package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import kr.wooco.woocobe.mysql.course.entity.CourseMetaJpaEntity

internal object CourseMetaPersistenceMapper {
    fun toProjection(courseMetaJpaEntity: CourseMetaJpaEntity): CourseMetaProjection =
        CourseMetaProjection(
            courseId = courseMetaJpaEntity.id,
            createdAt = courseMetaJpaEntity.createdAt,
            commentCount = courseMetaJpaEntity.commentCount,
            likeCount = courseMetaJpaEntity.likeCount,
            popularityScore = courseMetaJpaEntity.popularityScore,
        )

    fun toJpaEntity(courseMetaProjection: CourseMetaProjection): CourseMetaJpaEntity =
        CourseMetaJpaEntity(
            id = courseMetaProjection.courseId,
            createdAt = courseMetaProjection.createdAt,
            commentCount = courseMetaProjection.commentCount,
            likeCount = courseMetaProjection.likeCount,
            popularityScore = courseMetaProjection.popularityScore,
        )
}

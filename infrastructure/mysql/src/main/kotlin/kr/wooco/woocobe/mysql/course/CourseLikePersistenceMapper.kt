package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.entity.CourseLike
import kr.wooco.woocobe.mysql.course.entity.CourseLikeJpaEntity

internal object CourseLikePersistenceMapper {
    fun toDomainEntity(courseLikeJpaEntity: CourseLikeJpaEntity): CourseLike =
        CourseLike(
            id = courseLikeJpaEntity.id,
            userId = courseLikeJpaEntity.userId,
            courseId = courseLikeJpaEntity.courseId,
            status = CourseLike.Status.valueOf(courseLikeJpaEntity.status),
        )

    fun toJpaEntity(courseLike: CourseLike): CourseLikeJpaEntity =
        CourseLikeJpaEntity(
            id = courseLike.id,
            userId = courseLike.userId,
            courseId = courseLike.courseId,
            status = courseLike.status.name,
        )
}

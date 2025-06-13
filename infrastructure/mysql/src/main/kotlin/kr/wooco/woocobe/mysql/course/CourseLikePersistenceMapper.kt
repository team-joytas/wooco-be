package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.entity.LikeCourse
import kr.wooco.woocobe.mysql.course.entity.LikeCourseJpaEntity

internal object InterestCoursePersistenceMapper {
    fun toDomainEntity(likeCourseJpaEntity: LikeCourseJpaEntity): LikeCourse =
        LikeCourse(
            id = likeCourseJpaEntity.id,
            userId = likeCourseJpaEntity.userId,
            courseId = likeCourseJpaEntity.courseId,
            status = LikeCourse.Status.valueOf(likeCourseJpaEntity.status),
        )

    fun toJpaEntity(likeCourse: LikeCourse): LikeCourseJpaEntity =
        LikeCourseJpaEntity(
            id = likeCourse.id,
            userId = likeCourse.userId,
            courseId = likeCourse.courseId,
            status = likeCourse.status.name,
        )
}

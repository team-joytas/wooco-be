package kr.wooco.woocobe.course.infrastructure.storage

import kr.wooco.woocobe.course.domain.model.InterestCourse
import kr.wooco.woocobe.course.infrastructure.storage.entity.InterestCourseJpaEntity
import org.springframework.stereotype.Component

@Component
class InterestCourseStorageMapper {
    fun toDomain(interestCourseJpaEntity: InterestCourseJpaEntity): InterestCourse =
        InterestCourse(
            id = interestCourseJpaEntity.id,
            userId = interestCourseJpaEntity.userId,
            courseId = interestCourseJpaEntity.courseId,
        )

    fun toEntity(interestCourse: InterestCourse): InterestCourseJpaEntity =
        InterestCourseJpaEntity(
            id = interestCourse.id,
            userId = interestCourse.userId,
            courseId = interestCourse.courseId,
        )
}

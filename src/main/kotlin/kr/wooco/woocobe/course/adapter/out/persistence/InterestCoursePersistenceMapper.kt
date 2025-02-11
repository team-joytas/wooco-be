package kr.wooco.woocobe.course.adapter.out.persistence

import kr.wooco.woocobe.course.adapter.out.persistence.entity.InterestCourseJpaEntity
import kr.wooco.woocobe.course.domain.entity.InterestCourse
import org.springframework.stereotype.Component

@Component
internal class InterestCoursePersistenceMapper {
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

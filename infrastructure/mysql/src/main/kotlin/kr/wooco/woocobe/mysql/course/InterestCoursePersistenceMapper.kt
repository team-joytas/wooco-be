package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.entity.InterestCourse
import kr.wooco.woocobe.mysql.course.entity.InterestCourseJpaEntity
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

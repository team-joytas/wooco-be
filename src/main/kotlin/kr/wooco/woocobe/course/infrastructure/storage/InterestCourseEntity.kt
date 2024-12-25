package kr.wooco.woocobe.course.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.InterestCourse

@Entity
@Table(name = "interest_course")
class InterestCourseEntity(
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "interest_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "interest_course_id")
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(course: Course): InterestCourse =
        InterestCourse(
            id = id!!,
            userId = userId,
            course = course,
        )

    companion object {
        fun from(interestCourse: InterestCourse): InterestCourseEntity =
            with(interestCourse) {
                InterestCourseEntity(
                    id = id,
                    userId = userId,
                    courseId = course.id,
                )
            }
    }
}

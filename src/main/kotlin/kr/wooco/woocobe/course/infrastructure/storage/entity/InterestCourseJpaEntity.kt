package kr.wooco.woocobe.course.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.course.domain.model.InterestCourse

@Entity
@Table(name = "interest_course")
class InterestCourseJpaEntity(
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "interest_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "interest_course_id")
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(): InterestCourse =
        InterestCourse(
            id = id!!,
            userId = userId,
            courseId = courseId,
        )

    companion object {
        fun from(interestCourse: InterestCourse): InterestCourseJpaEntity =
            with(interestCourse) {
                InterestCourseJpaEntity(
                    id = id,
                    userId = userId,
                    courseId = courseId,
                )
            }
    }
}

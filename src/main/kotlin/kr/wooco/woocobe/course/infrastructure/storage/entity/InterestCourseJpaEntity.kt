package kr.wooco.woocobe.course.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity

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
) : BaseTimeEntity()

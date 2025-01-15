package kr.wooco.woocobe.course.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseEntity

@Entity
@Table(name = "interest_course")
class InterestCourseJpaEntity(
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "interest_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "interest_course_id")
    override val id: Long = 0L,
) : BaseEntity()

package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "interest_courses")
class InterestCourseJpaEntity(
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "interest_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "interest_course_id")
    override val id: Long = 0L,
) : BaseEntity()

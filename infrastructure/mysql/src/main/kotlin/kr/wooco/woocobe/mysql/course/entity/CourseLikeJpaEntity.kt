package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "course_likes")
class CourseLikeJpaEntity(
    @Column(name = "like_status")
    val status: String,
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "like_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "course_like_id")
    override val id: Long = 0L,
) : BaseEntity()

package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "course_meta")
data class CourseMetaJpaEntity(
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
    @Column(name = "comment_count")
    val commentCount: Long,
    @Column(name = "like_count")
    val likeCount: Long,
    @Column(name = "popularity_score")
    val popularityScore: Double,
    @Id
    @Column(name = "course_id")
    override val id: Long,
) : BaseEntity()

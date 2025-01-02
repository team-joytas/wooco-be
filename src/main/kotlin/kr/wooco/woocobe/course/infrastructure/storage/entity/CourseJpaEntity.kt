package kr.wooco.woocobe.course.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity

@Entity
@Table(name = "courses")
class CourseJpaEntity(
    @Column(name = "comment_count")
    val commentCount: Long,
    @Column(name = "interest_count")
    val interestCount: Long,
    @Column(name = "view_count")
    val viewCount: Long,
    @Column(columnDefinition = "text")
    val contents: String,
    @Column(name = "secondary_region", nullable = false)
    val secondaryRegion: String,
    @Column(name = "primary_region", nullable = false)
    val primaryRegion: String,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "course_id", nullable = false)
    val id: Long? = 0L,
) : BaseTimeEntity()

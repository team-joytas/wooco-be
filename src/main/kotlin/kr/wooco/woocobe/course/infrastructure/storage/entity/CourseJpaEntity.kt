package kr.wooco.woocobe.course.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "courses")
class CourseJpaEntity(
    @Column(name = "comment_count")
    val commentCount: Long,
    @Column(name = "interest_count")
    val interestCount: Long,
    @Column(name = "view_count")
    val viewCount: Long,
    @Column(name = "visit_date")
    val visitDate: LocalDate,
    @Column(columnDefinition = "text")
    val contents: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "course_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

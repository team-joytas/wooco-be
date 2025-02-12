package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid
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
    @Column(name = "title")
    val title: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "course_id")
    override val id: Long = 0L,
) : BaseTimeEntity()

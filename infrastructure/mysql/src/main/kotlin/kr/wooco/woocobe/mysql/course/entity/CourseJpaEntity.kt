package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid
import java.time.LocalDate

@Entity
@Table(name = "courses")
data class CourseJpaEntity(
    @Column(name = "course_status")
    val status: String,
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
) : BaseTimeEntity() {
    fun applyUpdate(course: Course): CourseJpaEntity =
        copy(
            title = course.content.title,
            contents = course.content.contents,
            primaryRegion = course.region.primaryRegion,
            secondaryRegion = course.region.secondaryRegion,
            visitDate = course.visitDate.value,
            status = course.status.name,
        )

    companion object {
        fun create(course: Course): CourseJpaEntity =
            CourseJpaEntity(
                userId = course.userId,
                title = course.content.title,
                contents = course.content.contents,
                primaryRegion = course.region.primaryRegion,
                secondaryRegion = course.region.secondaryRegion,
                visitDate = course.visitDate.value,
                status = course.status.name,
            )
    }
}

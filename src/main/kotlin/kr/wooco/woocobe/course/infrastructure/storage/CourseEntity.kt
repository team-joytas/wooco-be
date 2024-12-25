package kr.wooco.woocobe.course.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseCategory
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "courses")
class CourseEntity(
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
) : BaseTimeEntity() {
    fun toDomain(
        user: User,
        courseCategory: List<CourseCategory> = emptyList(),
    ): Course =
        Course(
            id = id!!,
            user = user,
            region = CourseRegion(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            ),
            writeDateTime = createdAt,
            categories = courseCategory.map { CourseCategory.from(it.name) },
            name = name,
            contents = contents,
            views = viewCount,
            comments = commentCount,
            interests = interestCount,
        )

    companion object {
        fun from(course: Course): CourseEntity =
            with(course) {
                CourseEntity(
                    id = id,
                    userId = user.id,
                    name = name,
                    primaryRegion = region.primaryRegion,
                    secondaryRegion = region.secondaryRegion,
                    contents = contents,
                    viewCount = views,
                    commentCount = comments,
                    interestCount = interests,
                )
            }
    }
}

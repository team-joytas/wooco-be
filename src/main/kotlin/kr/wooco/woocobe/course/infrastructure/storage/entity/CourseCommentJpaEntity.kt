package kr.wooco.woocobe.course.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.course.domain.model.CourseComment

@Entity
@Table(name = "course_comments")
class CourseCommentJpaEntity(
    @Column(columnDefinition = "text")
    val contents: String,
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "comment_user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "course_comment_id")
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(): CourseComment =
        CourseComment(
            id = id!!,
            userId = userId,
            courseId = courseId,
            contents = contents,
            commentDateTime = createdAt,
        )

    companion object {
        fun from(courseComment: CourseComment): CourseCommentJpaEntity =
            with(courseComment) {
                CourseCommentJpaEntity(
                    id = id,
                    userId = userId,
                    courseId = courseId,
                    contents = contents,
                )
            }
    }
}

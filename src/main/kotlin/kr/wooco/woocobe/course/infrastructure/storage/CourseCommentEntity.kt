package kr.wooco.woocobe.course.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "course_comment")
class CourseCommentEntity(
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
    fun toDomain(user: User): CourseComment =
        CourseComment(
            id = id!!,
            user = user,
            courseId = courseId,
            contents = contents,
            commentDateTime = createdAt,
        )

    companion object {
        fun from(courseComment: CourseComment): CourseCommentEntity =
            with(courseComment) {
                CourseCommentEntity(
                    id = id,
                    userId = user.id,
                    courseId = courseId,
                    contents = contents,
                )
            }
    }
}

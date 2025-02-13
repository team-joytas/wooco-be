package kr.wooco.woocobe.mysql.coursecomment.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

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
    override val id: Long = 0L,
) : BaseTimeEntity()

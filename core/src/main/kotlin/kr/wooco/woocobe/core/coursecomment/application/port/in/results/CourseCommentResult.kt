package kr.wooco.woocobe.core.coursecomment.application.port.`in`.results

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class CourseCommentResult(
    val commentId: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val writerId: Long,
    val writerName: String,
    val writerProfileUrl: String,
) {
    companion object {
        fun listOf(
            courseComments: List<CourseComment>,
            writers: List<User>,
        ): List<CourseCommentResult> {
            val writerMap = writers.associateBy { it.id }

            return courseComments.map { courseComment ->
                val writer = writerMap[courseComment.userId]!!

                CourseCommentResult(
                    commentId = courseComment.id,
                    contents = courseComment.contents.value,
                    createdAt = courseComment.createdAt,
                    writerId = writer.id,
                    writerName = writer.profile.name,
                    writerProfileUrl = writer.profile.profileUrl,
                )
            }
        }
    }
}

package kr.wooco.woocobe.course.ui.web.controller.response

import kr.wooco.woocobe.course.domain.model.CourseComment
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

data class CourseCommentDetailResponse(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val writer: CourseCommentWriterResponse,
) {
    companion object {
        fun listOf(
            courseComments: List<CourseComment>,
            users: List<User>,
        ): List<CourseCommentDetailResponse> {
            val userMap = users.associateBy { it.id }

            return courseComments.map { courseComment ->
                val writer = requireNotNull(userMap[courseComment.userId])

                CourseCommentDetailResponse(
                    id = courseComment.id,
                    contents = courseComment.contents,
                    createdAt = courseComment.commentDateTime,
                    writer = CourseCommentWriterResponse.from(writer),
                )
            }
        }
    }
}

data class CourseCommentWriterResponse(
    val id: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(user: User): CourseCommentWriterResponse =
            CourseCommentWriterResponse(
                id = user.id,
                name = user.name,
                profileUrl = user.profileUrl,
            )
    }
}

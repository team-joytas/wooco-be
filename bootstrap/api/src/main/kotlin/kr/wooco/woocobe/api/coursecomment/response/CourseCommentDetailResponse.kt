package kr.wooco.woocobe.api.coursecomment.response

import kr.wooco.woocobe.core.coursecomment.application.port.`in`.results.CourseCommentResult
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.core.user.domain.entity.User
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
                    contents = courseComment.contents.value,
                    createdAt = courseComment.createdAt,
                    writer = CourseCommentWriterResponse.from(writer),
                )
            }
        }

        fun listFrom(courseCommentResults: List<CourseCommentResult>): List<CourseCommentDetailResponse> =
            courseCommentResults.map {
                CourseCommentDetailResponse(
                    id = it.commentId,
                    contents = it.contents,
                    createdAt = it.createdAt,
                    writer = CourseCommentWriterResponse(
                        id = it.writerId,
                        name = it.writerName,
                        profileUrl = it.writerProfileUrl,
                    ),
                )
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
                name = user.profile.name,
                profileUrl = user.profile.profileUrl,
            )
    }
}

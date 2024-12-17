package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

class CourseComment(
    val id: Long,
    val user: User,
    val courseId: Long,
    var contents: String,
    val commentDateTime: LocalDateTime,
) {
    fun update(contents: String) =
        apply {
            this.contents = contents
        }

    fun isCommenter(targetId: Long): Boolean =
        when (targetId == user.id) {
            true -> true
            else -> throw RuntimeException()
        }

    companion object {
        fun register(
            user: User,
            courseId: Long,
            contents: String,
        ): CourseComment =
            CourseComment(
                id = 0L,
                user = user,
                courseId = courseId,
                contents = contents,
                commentDateTime = LocalDateTime.now(),
            )
    }
}

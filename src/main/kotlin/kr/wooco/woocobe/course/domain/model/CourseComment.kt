package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.course.domain.exception.InvalidCommentWriterException
import java.time.LocalDateTime

class CourseComment(
    val id: Long,
    val userId: Long,
    val courseId: Long,
    var contents: String,
    val commentDateTime: LocalDateTime,
) {
    fun update(contents: String) =
        apply {
            this.contents = contents
        }

    fun isValidCommenter(userId: Long) {
        if (this.userId != userId) throw InvalidCommentWriterException
    }

    companion object {
        fun register(
            userId: Long,
            courseId: Long,
            contents: String,
        ): CourseComment =
            CourseComment(
                id = 0L,
                userId = userId,
                courseId = courseId,
                contents = contents,
                commentDateTime = LocalDateTime.now(),
            )
    }
}

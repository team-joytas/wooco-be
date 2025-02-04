package kr.wooco.woocobe.coursecomment.domain.entity

import kr.wooco.woocobe.coursecomment.domain.exception.InvalidCommentWriterException
import java.time.LocalDateTime

class CourseComment(
    val id: Long,
    val userId: Long,
    val courseId: Long,
    var contents: Contents,
    val createdAt: LocalDateTime, // TODO: Read model 로 분리
) {
    constructor(userId: Long, courseId: Long, contents: String) : this(
        id = 0L,
        userId = userId,
        courseId = courseId,
        contents = Contents(
            value = contents,
        ),
        createdAt = LocalDateTime.now(),
    )

    @JvmInline
    value class Contents(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "댓글 내용이 없습니다." }
        }
    }

    fun updateContents(
        userId: Long,
        contents: String,
    ) {
        validateWriter(userId)

        this.contents = Contents(
            value = contents,
        )
    }

    fun validateWriter(userId: Long) {
        if (this.userId != userId) throw InvalidCommentWriterException
    }
}

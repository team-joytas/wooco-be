package kr.wooco.woocobe.core.coursecomment.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.coursecomment.domain.command.CreateCommentCommand
import kr.wooco.woocobe.core.coursecomment.domain.command.DeleteCommentCommand
import kr.wooco.woocobe.core.coursecomment.domain.command.UpdateCommentContentsCommand
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentDeletedEvent
import kr.wooco.woocobe.core.coursecomment.domain.exception.InvalidCommentWriterException
import java.time.LocalDateTime

// TODO: Read model 추가시 createdAt 제거

data class CourseComment(
    override val id: Long,
    val userId: Long,
    val courseId: Long,
    val contents: Contents,
    val status: Status,
    val createdAt: LocalDateTime,
) : AggregateRoot() {
    enum class Status {
        ACTIVE,
        DELETED,
    }

    @JvmInline
    value class Contents(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "댓글 내용이 없습니다." }
        }
    }

    fun updateContents(command: UpdateCommentContentsCommand): CourseComment {
        when {
            status == Status.DELETED -> throw RuntimeException("이미 삭제됨")
            userId != command.userId -> throw InvalidCommentWriterException
        }

        return copy(
            contents = command.contents,
        )
    }

    fun delete(command: DeleteCommentCommand): CourseComment {
        when {
            status == Status.DELETED -> throw RuntimeException("이미 삭제됨")
            userId != command.userId -> throw InvalidCommentWriterException
        }

        return copy(
            status = Status.DELETED,
        ).also {
            registerEvent(CourseCommentDeletedEvent.from(it))
        }
    }

    companion object {
        fun create(
            command: CreateCommentCommand,
            identifier: (CourseComment) -> Long,
        ): CourseComment =
            CourseComment(
                id = 0L,
                userId = command.userId,
                courseId = command.courseId,
                contents = command.contents,
                status = Status.ACTIVE,
                createdAt = LocalDateTime.now(),
            ).let {
                it.copy(id = identifier.invoke(it))
            }.also {
                it.registerEvent(
                    CourseCommentCreateEvent.of(
                        courseTitle = command.courseTitle,
                        command.courseWriterId,
                        it,
                    ),
                )
            }
    }
}

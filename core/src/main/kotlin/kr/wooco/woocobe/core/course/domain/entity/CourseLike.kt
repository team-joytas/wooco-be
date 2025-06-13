package kr.wooco.woocobe.core.course.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.course.domain.command.CreateLikeCourseCommand
import kr.wooco.woocobe.core.course.domain.event.LikeCourseCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.LikeCourseDeletedEvent
import kr.wooco.woocobe.core.course.domain.exception.AlreadyLikedCourseException
import kr.wooco.woocobe.core.course.domain.exception.NotExistsInterestCourseException

data class LikeCourse(
    override val id: Long,
    val userId: Long,
    val courseId: Long,
    val status: Status,
) : AggregateRoot() {
    enum class Status {
        ACTIVE,
        DELETED,
    }

    fun active(): LikeCourse {
        when {
            status == Status.ACTIVE -> throw AlreadyLikedCourseException
        }

        return copy(
            status = Status.ACTIVE,
        ).also {
            it.registerEvent(LikeCourseCreatedEvent.of(it))
        }
    }

    fun delete(): LikeCourse {
        when {
            status == Status.DELETED -> throw NotExistsInterestCourseException
        }

        return copy(
            status = Status.DELETED,
        ).also {
            it.registerEvent(LikeCourseDeletedEvent.of(it))
        }
    }

    companion object {
        fun create(
            command: CreateLikeCourseCommand,
            identifier: (LikeCourse) -> Long,
        ): LikeCourse =
            LikeCourse(
                id = 0L,
                userId = command.userId,
                courseId = command.courseId,
                status = Status.ACTIVE,
            ).let {
                it.copy(id = identifier.invoke(it))
            }.also {
                it.registerEvent(LikeCourseCreatedEvent.of(it))
            }
    }
}

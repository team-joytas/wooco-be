package kr.wooco.woocobe.core.coursecomment.domain.event

import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

class CourseCommentCreateEvent(
    val courseId: Long,
    val courseWriterId: Long,
    val courseCommentId: Long,
    val commentWriterId: Long,
) {
    companion object {
        fun of(
            course: Course,
            courseComment: CourseComment,
        ): CourseCommentCreateEvent =
            CourseCommentCreateEvent(
                courseId = course.id,
                courseWriterId = course.userId,
                courseCommentId = courseComment.id,
                commentWriterId = courseComment.userId,
            )
    }
}

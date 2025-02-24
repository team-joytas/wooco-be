package kr.wooco.woocobe.core.coursecomment.domain.event

import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

class CourseCommentCreateEvent(
    val courseId: Long,
    val courseTitle: String,
    val courseWriterId: Long,
    val commentWriterId: Long,
) {
    companion object {
        fun of(
            course: Course,
            courseComment: CourseComment,
        ): CourseCommentCreateEvent =
            CourseCommentCreateEvent(
                courseId = course.id,
                courseTitle = course.title,
                courseWriterId = course.userId,
                commentWriterId = courseComment.userId,
            )
    }
}

package kr.wooco.woocobe.core.coursecomment.application.port.out

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

interface CourseCommentCommandPort {
    fun saveCourseComment(courseComment: CourseComment): Long

    fun getByCourseCommentId(courseCommentId: Long): CourseComment
}

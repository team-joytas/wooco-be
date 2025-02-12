package kr.wooco.woocobe.core.coursecomment.application.port.out

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

interface SaveCourseCommentPersistencePort {
    fun saveCourseComment(courseComment: CourseComment): CourseComment
}

package kr.wooco.woocobe.coursecomment.application.port.out

import kr.wooco.woocobe.coursecomment.domain.entity.CourseComment

interface SaveCourseCommentPersistencePort {
    fun saveCourseComment(courseComment: CourseComment): CourseComment
}

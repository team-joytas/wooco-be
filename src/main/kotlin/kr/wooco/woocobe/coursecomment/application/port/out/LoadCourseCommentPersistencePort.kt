package kr.wooco.woocobe.coursecomment.application.port.out

import kr.wooco.woocobe.coursecomment.domain.entity.CourseComment

interface LoadCourseCommentPersistencePort {
    fun getByCourseCommentId(courseCommentId: Long): CourseComment

    fun getAllByCourseId(courseId: Long): List<CourseComment>
}

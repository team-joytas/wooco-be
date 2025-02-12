package kr.wooco.woocobe.core.coursecomment.application.port.out

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

interface LoadCourseCommentPersistencePort {
    fun getByCourseCommentId(courseCommentId: Long): CourseComment

    fun getAllByCourseId(courseId: Long): List<CourseComment>
}

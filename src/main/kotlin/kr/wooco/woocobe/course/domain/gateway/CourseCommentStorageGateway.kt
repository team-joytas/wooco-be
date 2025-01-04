package kr.wooco.woocobe.course.domain.gateway

import kr.wooco.woocobe.course.domain.model.CourseComment

interface CourseCommentStorageGateway {
    fun save(courseComment: CourseComment): CourseComment

    fun getByCommentId(commentId: Long): CourseComment

    fun getAllByCourseId(courseId: Long): List<CourseComment>

    fun deleteByCommentId(commentId: Long)
}

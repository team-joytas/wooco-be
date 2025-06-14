package kr.wooco.woocobe.core.coursecomment.application.port.out

import kr.wooco.woocobe.core.coursecomment.application.port.out.dto.CourseCommentView

interface CourseCommentQueryPort {
    fun getAllViewByCourseId(courseId: Long): List<CourseCommentView>
}

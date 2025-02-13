package kr.wooco.woocobe.core.coursecomment.application.port.out

interface DeleteCourseCommentPersistencePort {
    fun deleteByCourseCommentId(courseCommentId: Long)
}

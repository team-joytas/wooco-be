package kr.wooco.woocobe.coursecomment.application.port.out

interface DeleteCourseCommentPersistencePort {
    fun deleteByCourseCommentId(courseCommentId: Long)
}

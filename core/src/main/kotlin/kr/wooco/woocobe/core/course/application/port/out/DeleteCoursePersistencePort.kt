package kr.wooco.woocobe.core.course.application.port.out

interface DeleteCoursePersistencePort {
    fun deleteByCourseId(courseId: Long)
}

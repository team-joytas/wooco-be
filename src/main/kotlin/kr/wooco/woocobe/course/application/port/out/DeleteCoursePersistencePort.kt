package kr.wooco.woocobe.course.application.port.out

interface DeleteCoursePersistencePort {
    fun deleteByCourseId(courseId: Long)
}

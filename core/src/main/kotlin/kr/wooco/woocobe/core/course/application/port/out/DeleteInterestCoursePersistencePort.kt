package kr.wooco.woocobe.core.course.application.port.out

interface DeleteInterestCoursePersistencePort {
    fun deleteByInterestCourseId(interestCourseId: Long)
}

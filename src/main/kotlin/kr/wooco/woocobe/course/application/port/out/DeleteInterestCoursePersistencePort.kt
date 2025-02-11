package kr.wooco.woocobe.course.application.port.out

interface DeleteInterestCoursePersistencePort {
    fun deleteByInterestCourseId(interestCourseId: Long)
}

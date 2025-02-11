package kr.wooco.woocobe.course.application.port.out

import kr.wooco.woocobe.course.domain.entity.InterestCourse

interface LoadInterestCoursePersistencePort {
    fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean

    fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourse

    fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>
}

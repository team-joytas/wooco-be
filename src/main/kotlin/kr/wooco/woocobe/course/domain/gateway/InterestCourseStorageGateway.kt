package kr.wooco.woocobe.course.domain.gateway

import kr.wooco.woocobe.course.domain.model.InterestCourse

interface InterestCourseStorageGateway {
    fun save(interestCourse: InterestCourse): InterestCourse

    fun getByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): InterestCourse

    fun getAllByUserId(
        userId: Long,
        limit: Int?,
    ): List<InterestCourse>

    fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean

    fun getInterestCourseIdsByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>

    fun deleteByInterestCourseId(interestCourseId: Long)
}

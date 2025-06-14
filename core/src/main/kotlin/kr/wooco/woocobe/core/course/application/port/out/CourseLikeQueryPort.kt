package kr.wooco.woocobe.core.course.application.port.out

interface CourseLikeQueryPort {
    /**
     * 좋아요 상태가 `ACTIVE` 인 좋아요 개수를 측정
     */
    fun countByUserId(userId: Long): Long

    /**
     * 특정 코스 ids 중 좋아요 상태가 `ACTIVE` 인 courseId 반환
     */
    fun getAllCourseIdByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>

    fun existsByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): Boolean
}

package kr.wooco.woocobe.core.course.application.readmodel

import kr.wooco.woocobe.core.course.application.policy.CoursePopularityScorePolicy
import java.time.LocalDateTime

data class CourseMetaProjection(
    val courseId: Long,
    val createdAt: LocalDateTime,
    val commentCount: Long,
    val likeCount: Long,
    val popularityScore: Double,
) {
    fun increaseComments(): CourseMetaProjection = copy(commentCount = commentCount + 1)

    fun decreaseComments(): CourseMetaProjection = copy(commentCount = (commentCount - 1).coerceAtLeast(0))

    fun increaseLikes(): CourseMetaProjection = recalculateLikeProjection(nextLikeCount = likeCount + 1)

    fun decreaseLikes(): CourseMetaProjection = recalculateLikeProjection(nextLikeCount = (likeCount - 1).coerceAtLeast(0))

    private fun recalculateLikeProjection(nextLikeCount: Long): CourseMetaProjection =
        copy(
            likeCount = nextLikeCount,
            popularityScore = CoursePopularityScorePolicy.calculate(nextLikeCount, createdAt),
        )

    companion object {
        fun initialize(
            courseId: Long,
            createdAt: LocalDateTime,
        ): CourseMetaProjection =
            CourseMetaProjection(
                courseId = courseId,
                createdAt = createdAt,
                commentCount = 0,
                likeCount = 0,
                popularityScore = 0.0,
            )
    }
}

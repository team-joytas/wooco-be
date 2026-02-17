package kr.wooco.woocobe.core.course.application.policy

import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.log10

object CoursePopularityScorePolicy {
    private const val HOT_SCORE_DIVISOR = 45_000.0

    fun calculate(
        likeCount: Long,
        createdAt: LocalDateTime,
    ): Double {
        val timeScore = createdAt.toEpochSecond(ZoneOffset.UTC) / HOT_SCORE_DIVISOR
        return log10(likeCount + 1.0) + timeScore
    }
}

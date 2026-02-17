package kr.wooco.woocobe.core.course.application.policy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CoursePopularityScorePolicyTest {
    @Test
    @DisplayName("좋아요가 0개여도 생성시점 기반 기본 점수는 유지된다")
    fun calculateBaseScoreWhenNoLikes() {
        val createdAt = LocalDateTime.of(2026, 3, 10, 0, 0)

        assertThat(CoursePopularityScorePolicy.calculate(0, createdAt)).isGreaterThan(0.0)
    }

    @Test
    @DisplayName("동일한 좋아요 수라면 더 최근에 생성된 코스 점수가 더 높다")
    fun calculateHigherScoreForMoreRecentCourse() {
        val oldCreatedAt = LocalDateTime.of(2026, 3, 1, 0, 0)
        val recentCreatedAt = LocalDateTime.of(2026, 3, 10, 0, 0)

        val oldScore = CoursePopularityScorePolicy.calculate(10, oldCreatedAt)
        val recentScore = CoursePopularityScorePolicy.calculate(10, recentCreatedAt)

        assertThat(recentScore).isGreaterThan(oldScore)
    }

    @Test
    @DisplayName("동일한 시점이라면 좋아요 수가 많은 코스 점수가 더 높다")
    fun calculateHigherScoreForMoreLikes() {
        val createdAt = LocalDateTime.of(2026, 3, 10, 0, 0)

        val lowScore = CoursePopularityScorePolicy.calculate(10, createdAt)
        val highScore = CoursePopularityScorePolicy.calculate(100, createdAt)

        assertThat(highScore).isGreaterThan(lowScore)
    }
}

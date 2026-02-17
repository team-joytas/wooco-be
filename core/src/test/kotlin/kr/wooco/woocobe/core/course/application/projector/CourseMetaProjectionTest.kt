package kr.wooco.woocobe.core.course.application.projector

import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CourseMetaProjectionTest {
    @Test
    @DisplayName("projection 초기화 시 댓글/좋아요/점수는 0이다")
    fun initializeProjection() {
        val createdAt = LocalDateTime.of(2026, 3, 1, 0, 0)
        val projection = CourseMetaProjection.initialize(1L, createdAt)

        assertThat(projection.courseId).isEqualTo(1L)
        assertThat(projection.createdAt).isEqualTo(createdAt)
        assertThat(projection.commentCount).isZero()
        assertThat(projection.likeCount).isZero()
        assertThat(projection.popularityScore).isZero()
    }

    @Test
    @DisplayName("댓글 증감은 projection 내부 로직으로 처리한다")
    fun increaseAndDecreaseComments() {
        val createdAt = LocalDateTime.of(2026, 3, 1, 0, 0)
        val projection = CourseMetaProjection
            .initialize(1L, createdAt)
            .increaseComments()
            .increaseComments()
            .decreaseComments()

        assertThat(projection.commentCount).isEqualTo(1L)
    }

    @Test
    @DisplayName("좋아요 증감 시 likeCount 와 popularityScore 를 함께 갱신한다")
    fun increaseAndDecreaseLikes() {
        val createdAt = LocalDateTime.of(2026, 3, 10, 0, 0)

        val increased = CourseMetaProjection.initialize(1L, createdAt).increaseLikes()
        val decreased = increased.decreaseLikes()

        assertThat(increased.likeCount).isEqualTo(1L)
        assertThat(increased.popularityScore).isGreaterThan(0.0)
        assertThat(decreased.likeCount).isZero()
        assertThat(decreased.popularityScore).isGreaterThan(0.0)
    }
}

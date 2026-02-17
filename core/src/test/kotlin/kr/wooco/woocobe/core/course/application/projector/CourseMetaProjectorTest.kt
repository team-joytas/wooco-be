package kr.wooco.woocobe.core.course.application.projector

import kr.wooco.woocobe.core.course.application.port.out.CourseMetaProjectionPort
import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import kr.wooco.woocobe.core.course.domain.event.CourseCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.CourseLikeCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.CourseLikeDeletedEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CourseMetaProjectorTest {
    @Test
    @DisplayName("코스 생성 이벤트가 오면 course meta projection 을 초기화한다")
    fun initializeProjectionOnCourseCreated() {
        val createdAt = LocalDateTime.of(2026, 3, 1, 0, 0)
        val port = FakeCourseMetaProjectionPort(createdAt = createdAt)
        val projector = CourseMetaProjector(courseMetaProjectionPort = port)

        projector.handleCourseCreatedEvent(CourseCreatedEvent(aggregateId = 1L))

        assertThat(port.savedProjection).isEqualTo(CourseMetaProjection.initialize(1L, createdAt))
    }

    @Test
    @DisplayName("좋아요 생성 이벤트가 오면 projection 의 좋아요 수와 점수를 갱신한다")
    fun updateProjectionOnLikeCreated() {
        val port =
            FakeCourseMetaProjectionPort(
                createdAt = LocalDateTime.of(2026, 3, 12, 12, 0),
                projection = CourseMetaProjection.initialize(1L, LocalDateTime.of(2026, 3, 12, 12, 0)),
            )
        val projector = CourseMetaProjector(courseMetaProjectionPort = port)

        projector.handleLikeCreatedEvent(
            CourseLikeCreatedEvent(
                aggregateId = 100L,
                courseId = 1L,
            ),
        )

        assertThat(port.savedProjection!!.likeCount).isEqualTo(1L)
        assertThat(port.savedProjection!!.popularityScore).isGreaterThan(0.0)
    }

    @Test
    @DisplayName("좋아요 삭제 이벤트가 오면 projection 의 좋아요 수와 점수를 갱신한다")
    fun updateProjectionOnLikeDeleted() {
        val port =
            FakeCourseMetaProjectionPort(
                createdAt = LocalDateTime.of(2026, 3, 1, 12, 0),
                projection = CourseMetaProjection(
                    courseId = 1L,
                    createdAt = LocalDateTime.of(2026, 3, 1, 12, 0),
                    commentCount = 0L,
                    likeCount = 1L,
                    popularityScore = 1.0,
                ),
            )
        val projector = CourseMetaProjector(courseMetaProjectionPort = port)

        projector.handleLikeDeletedEvent(
            CourseLikeDeletedEvent(
                aggregateId = 100L,
                courseId = 1L,
            ),
        )

        assertThat(port.savedProjection!!.likeCount).isZero()
        assertThat(port.savedProjection!!.popularityScore).isGreaterThan(0.0)
    }

    @Test
    @DisplayName("댓글 생성 이벤트가 오면 projection 댓글 수를 증가시킨다")
    fun increaseCommentsOnCommentCreated() {
        val port =
            FakeCourseMetaProjectionPort(
                createdAt = LocalDateTime.of(2026, 3, 1, 12, 0),
                projection = CourseMetaProjection.initialize(1L, LocalDateTime.of(2026, 3, 1, 12, 0)),
            )
        val projector = CourseMetaProjector(courseMetaProjectionPort = port)

        projector.handleCommentCreatedEvent(
            CourseCommentCreateEvent(
                aggregateId = 1L,
                courseId = 1L,
                courseTitle = "title",
                courseWriterId = 2L,
                commentWriterId = 3L,
            ),
        )

        assertThat(port.savedProjection!!.commentCount).isEqualTo(1L)
    }

    private class FakeCourseMetaProjectionPort(
        private val createdAt: LocalDateTime,
        projection: CourseMetaProjection? = null,
    ) : CourseMetaProjectionPort {
        var savedProjection: CourseMetaProjection? = projection

        override fun getByCourseId(courseId: Long): CourseMetaProjection = savedProjection ?: error("projection not initialized")

        override fun save(courseMetaProjection: CourseMetaProjection) {
            savedProjection = courseMetaProjection
        }

        override fun getCreatedAtByCourseId(courseId: Long): LocalDateTime = createdAt
    }
}

package kr.wooco.woocobe.course.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestCourseJpaRepository : JpaRepository<InterestCourseEntity, Long> {
    fun findByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourseEntity?

    @Query(
        """
            SELECT CASE WHEN COUNT(ic) > 0 THEN true ELSE false END
            FROM InterestCourseEntity ic
            WHERE ic.courseId = :courseId AND ic.userId = :userId
        """,
    )
    fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean

    fun findAllByUserId(userId: Long): List<InterestCourseEntity>
}

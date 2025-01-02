package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.InterestCourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestCourseJpaRepository : JpaRepository<InterestCourseEntity, Long> {
    fun findByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourseEntity?

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM InterestCourseEntity ic
                WHERE ic.courseId = :courseId
                    AND ic.userId = :userId
            ) THEN true ELSE false END
        """,
    )
    fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean

    fun findAllByUserId(userId: Long): List<InterestCourseEntity>
}

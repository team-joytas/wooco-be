package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.InterestCourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestCourseJpaRepository : JpaRepository<InterestCourseJpaEntity, Long> {
    fun findByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): InterestCourseJpaEntity?

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM InterestCourseJpaEntity ic
                WHERE ic.courseId = :courseId
                    AND ic.userId = :userId
            ) THEN true ELSE false END
        """,
    )
    fun existsByCourseIdAndUserId(
        courseId: Long,
        userId: Long,
    ): Boolean

    fun findAllByUserId(userId: Long): List<InterestCourseJpaEntity>
}

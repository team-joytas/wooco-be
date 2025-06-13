package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.LikeCourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestCourseJpaRepository : JpaRepository<LikeCourseJpaEntity, Long> {
    fun findByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): LikeCourseJpaEntity?

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM LikeCourseJpaEntity lc
                WHERE lc.courseId = :courseId
                    AND lc.userId = :userId
                    AND lc.status = 'ACTIVE'
            ) THEN true ELSE false END
        """,
    )
    fun existsByCourseIdAndUserIdAndActive(
        courseId: Long,
        userId: Long,
    ): Boolean

    @Query(
        """
           SELECT lc.courseId
           FROM LikeCourseJpaEntity lc
           WHERE lc.userId = :userId
                AND lc.status = 'ACTIVE'
                AND lc.courseId IN :courseIds
        """,
    )
    fun findCourseIdsByUserIdAndCourseIdsAndActive(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>

    @Query(
        """
           SELECT COUNT (lc.id)
           FROM LikeCourseJpaEntity lc
           WHERE lc.status = 'ACTIVE'
        """,
    )
    fun countByUserIdAndActive(userId: Long): Long
}

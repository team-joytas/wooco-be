package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.InterestCourseJpaEntity
import org.springframework.data.domain.Pageable
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

    @Query(
        """
           SELECT ic.courseId
           FROM InterestCourseJpaEntity ic
           WHERE ic.userId = :userId
                AND ic.courseId IN :courseIds
        """,
    )
    fun findCourseIdsByUserIdAndCourseIds(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>

    fun findAllByUserId(
        userId: Long,
        pageable: Pageable,
    ): List<InterestCourseJpaEntity>

    fun countByUserId(userId: Long): Long
}

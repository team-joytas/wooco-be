package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseLikeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseLikeJpaRepository : JpaRepository<CourseLikeJpaEntity, Long> {
    fun findByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): CourseLikeJpaEntity?

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM CourseLikeJpaEntity cl
                JOIN CourseJpaEntity c ON c.id = cl.courseId
                WHERE cl.courseId = :courseId
                    AND cl.userId = :userId
                    AND cl.status = 'ACTIVE'
                    AND c.status = 'ACTIVE'
            ) THEN true ELSE false END
        """,
    )
    fun existsByCourseIdAndUserIdAndActive(
        courseId: Long,
        userId: Long,
    ): Boolean

    @Query(
        """
            SELECT cl.courseId
            FROM CourseLikeJpaEntity cl
            JOIN CourseJpaEntity c ON c.id = cl.courseId
            WHERE cl.userId = :userId
                AND cl.status = 'ACTIVE'
                AND cl.courseId IN :courseIds
                AND c.status = 'ACTIVE'
        """,
    )
    fun findCourseIdsByUserIdAndCourseIdsAndActive(
        userId: Long,
        courseIds: List<Long>,
    ): List<Long>

    @Query(
        """
            SELECT COUNT (cl.id)
            FROM CourseLikeJpaEntity cl
            JOIN CourseJpaEntity c ON c.id = cl.courseId
            WHERE cl.userId = :userId
                AND cl.status = 'ACTIVE'
                AND c.status = 'ACTIVE'
        """,
    )
    fun countByUserIdAndActive(userId: Long): Long
}

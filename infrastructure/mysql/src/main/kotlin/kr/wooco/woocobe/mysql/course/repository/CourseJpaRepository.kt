package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseJpaEntity, Long>, CourseCustomRepository {
    @Query("SELECT c FROM CourseJpaEntity c WHERE c.status = 'ACTIVE' AND c.id = :courseId")
    fun findByCourseIdWithActiveOrNull(courseId: Long): CourseJpaEntity?

    @Query("SELECT c.createdAt FROM CourseJpaEntity c WHERE c.id = :courseId")
    fun findCreatedAtByCourseId(courseId: Long): LocalDateTime?

    fun countByUserId(userId: Long): Long

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM CourseJpaEntity c
                WHERE c.id = :courseId
                    AND c.status = 'ACTIVE'
            ) THEN true ELSE false END
        """,
    )
    fun existsByCourseIdAndActive(courseId: Long): Boolean
}

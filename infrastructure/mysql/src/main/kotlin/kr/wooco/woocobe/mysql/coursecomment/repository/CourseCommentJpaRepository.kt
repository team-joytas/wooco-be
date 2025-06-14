package kr.wooco.woocobe.mysql.coursecomment.repository

import kr.wooco.woocobe.mysql.coursecomment.entity.CourseCommentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseCommentJpaRepository : JpaRepository<CourseCommentJpaEntity, Long> {
    @Query(
        """
            SELECT cc FROM CourseCommentJpaEntity cc
            WHERE cc.courseId = :courseId
                AND cc.status = 'ACTIVE'
        """,
    )
    fun findAllByCourseIdAndActive(courseId: Long): List<CourseCommentJpaEntity>
}

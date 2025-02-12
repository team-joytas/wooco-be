package kr.wooco.woocobe.mysql.coursecomment.repository

import kr.wooco.woocobe.mysql.coursecomment.entity.CourseCommentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCommentJpaRepository : JpaRepository<CourseCommentJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCommentJpaEntity>
}

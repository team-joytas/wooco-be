package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCommentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCommentJpaRepository : JpaRepository<CourseCommentJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCommentJpaEntity>
}

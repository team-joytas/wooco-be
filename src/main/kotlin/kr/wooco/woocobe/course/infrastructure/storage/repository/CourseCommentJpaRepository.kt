package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCommentJpaRepository : JpaRepository<CourseCommentEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCommentEntity>
}

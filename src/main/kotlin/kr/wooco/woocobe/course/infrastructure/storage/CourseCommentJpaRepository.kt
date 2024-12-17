package kr.wooco.woocobe.course.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface CourseCommentJpaRepository : JpaRepository<CourseCommentEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCommentEntity>
}

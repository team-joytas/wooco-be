package kr.wooco.woocobe.coursecomment.adapter.out.persistence.repository

import kr.wooco.woocobe.coursecomment.adapter.out.persistence.entity.CourseCommentJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCommentJpaRepository : JpaRepository<CourseCommentJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCommentJpaEntity>
}

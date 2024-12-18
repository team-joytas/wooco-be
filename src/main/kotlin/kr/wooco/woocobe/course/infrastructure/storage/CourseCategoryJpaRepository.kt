package kr.wooco.woocobe.course.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface CourseCategoryJpaRepository : JpaRepository<CourseCategoryEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCategoryEntity>

    fun findAllByCourseIdIn(courseIds: List<Long>): List<CourseCategoryEntity>
}

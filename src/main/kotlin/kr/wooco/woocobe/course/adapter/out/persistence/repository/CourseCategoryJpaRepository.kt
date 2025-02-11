package kr.wooco.woocobe.course.adapter.out.persistence.repository

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseCategoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCategoryJpaRepository : JpaRepository<CourseCategoryJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCategoryJpaEntity>

    fun findAllByCourseIdIn(courseIds: List<Long>): List<CourseCategoryJpaEntity>

    fun deleteAllByCourseId(courseId: Long)
}

package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCategoryJpaRepository : JpaRepository<CourseCategoryJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CourseCategoryJpaEntity>

    fun findAllByCourseIdIn(courseIds: List<Long>): List<CourseCategoryJpaEntity>
}

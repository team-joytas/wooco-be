package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseMetaJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseMetaJpaRepository : JpaRepository<CourseMetaJpaEntity, Long> {
    fun findAllByIdIn(courseIds: List<Long>): List<CourseMetaJpaEntity>
}

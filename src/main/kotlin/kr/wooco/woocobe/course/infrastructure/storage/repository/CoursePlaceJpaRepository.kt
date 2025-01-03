package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.CoursePlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CoursePlaceJpaRepository : JpaRepository<CoursePlaceJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CoursePlaceJpaEntity>

    fun findAllByCourseIdIn(courseIds: List<Long>): List<CoursePlaceJpaEntity>

    fun deleteAllByCourseId(courseId: Long)
}

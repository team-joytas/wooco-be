package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CoursePlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CoursePlaceJpaRepository : JpaRepository<CoursePlaceJpaEntity, Long> {
    fun findAllByCourseId(courseId: Long): List<CoursePlaceJpaEntity>

    fun findAllByCourseIdIn(courseIds: List<Long>): List<CoursePlaceJpaEntity>

    fun deleteAllByCourseId(courseId: Long)
}

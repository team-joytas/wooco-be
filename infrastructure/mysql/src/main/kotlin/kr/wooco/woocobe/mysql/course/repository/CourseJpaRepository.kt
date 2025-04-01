package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseJpaEntity, Long>, CourseCustomRepository {
    fun countByUserId(userId: Long): Long
}

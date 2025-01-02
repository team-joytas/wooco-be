package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseEntity, Long>, CourseCustomRepository

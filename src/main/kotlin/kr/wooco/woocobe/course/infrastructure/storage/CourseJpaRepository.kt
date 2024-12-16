package kr.wooco.woocobe.course.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseEntity, Long>, CourseCustomRepository

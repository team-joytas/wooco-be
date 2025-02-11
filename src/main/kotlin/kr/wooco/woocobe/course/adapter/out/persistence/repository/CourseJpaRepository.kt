package kr.wooco.woocobe.course.adapter.out.persistence.repository

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseJpaEntity, Long>, CourseCustomRepository

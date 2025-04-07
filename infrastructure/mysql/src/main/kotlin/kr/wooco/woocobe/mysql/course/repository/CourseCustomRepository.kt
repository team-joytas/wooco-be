package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.core.course.application.port.out.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity

interface CourseCustomRepository {
    fun findAllCourseByCondition(condition: CourseSearchCondition): List<CourseJpaEntity>

    fun findAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseJpaEntity>
}

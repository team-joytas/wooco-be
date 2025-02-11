package kr.wooco.woocobe.course.adapter.out.persistence.repository

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseJpaEntity
import kr.wooco.woocobe.course.application.service.dto.CourseSearchCondition
import kr.wooco.woocobe.course.application.service.dto.InterestCourseSearchCondition

interface CourseCustomRepository {
//    fun findAllByUserIdWithSort(
//        userId: Long,
//        sort: CourseSortCondition,
//    ): List<CourseJpaEntity>
//
//    fun findAllByRegionAndCategoryWithSort(
//        primaryRegion: String?,
//        secondaryRegion: String?,
//        category: String?,
//        sort: CourseSortCondition,
//        limit: Int?,
//    ): List<CourseJpaEntity>

    fun findAllCourseByCondition(condition: CourseSearchCondition): List<CourseJpaEntity>

    fun findAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseJpaEntity>
}

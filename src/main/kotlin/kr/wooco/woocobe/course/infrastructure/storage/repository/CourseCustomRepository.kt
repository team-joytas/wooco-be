package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseEntity

interface CourseCustomRepository {
    fun findAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<CourseEntity>

    fun findAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<CourseEntity>
}

package kr.wooco.woocobe.course.infrastructure.storage.repository

import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseJpaEntity

interface CourseCustomRepository {
    fun findAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<CourseJpaEntity>

    fun findAllByRegionAndCategoryWithSort(
        primaryRegion: String?,
        secondaryRegion: String?,
        category: String?,
        sort: CourseSortCondition,
        limit: Int?,
    ): List<CourseJpaEntity>
}

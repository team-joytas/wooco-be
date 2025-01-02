package kr.wooco.woocobe.course.domain.gateway

import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition

interface CourseStorageGateway {
    fun save(course: Course): Course

    fun getByCourseId(courseId: Long): Course?

    fun getAllByCourseIds(courseIds: List<Long>): List<Course>

    fun getAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<Course>

    fun getAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<Course>

    fun deleteByCourseId(courseId: Long)
}

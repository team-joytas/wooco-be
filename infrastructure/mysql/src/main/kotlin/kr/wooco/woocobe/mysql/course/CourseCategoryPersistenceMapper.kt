package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity

internal object CourseCategoryPersistenceMapper {
    fun toJpaEntity(
        courseJpaEntity: CourseJpaEntity,
        courseCategory: CourseCategory,
    ): CourseCategoryJpaEntity =
        CourseCategoryJpaEntity(
            courseId = courseJpaEntity.id,
            name = courseCategory.name,
        )
}

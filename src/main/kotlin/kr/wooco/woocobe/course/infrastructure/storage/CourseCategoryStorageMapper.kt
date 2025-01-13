package kr.wooco.woocobe.course.infrastructure.storage

import kr.wooco.woocobe.course.domain.model.CourseCategory
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseJpaEntity
import org.springframework.stereotype.Component

@Component
class CourseCategoryStorageMapper {
    fun toEntity(
        courseJpaEntity: CourseJpaEntity,
        courseCategory: CourseCategory,
    ): CourseCategoryJpaEntity =
        CourseCategoryJpaEntity(
            courseId = courseJpaEntity.id,
            name = courseCategory.name,
        )
}

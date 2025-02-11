package kr.wooco.woocobe.course.adapter.out.persistence

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseJpaEntity
import kr.wooco.woocobe.course.domain.vo.CourseCategory
import org.springframework.stereotype.Component

@Component
internal class CourseCategoryPersistenceMapper {
    fun toEntity(
        courseJpaEntity: CourseJpaEntity,
        courseCategory: CourseCategory,
    ): CourseCategoryJpaEntity =
        CourseCategoryJpaEntity(
            courseId = courseJpaEntity.id,
            name = courseCategory.name,
        )
}

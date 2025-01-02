package kr.wooco.woocobe.course.infrastructure.storage

import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseCategory
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseJpaEntity
import org.springframework.stereotype.Component

@Component
class CourseStorageMapper {
    fun toDomain(
        courseJpaEntity: CourseJpaEntity,
        courseCategoryJpaEntities: List<CourseCategoryJpaEntity>,
    ): Course =
        Course(
            id = courseJpaEntity.id!!,
            userId = courseJpaEntity.userId,
            region = CourseRegion(
                primaryRegion = courseJpaEntity.primaryRegion,
                secondaryRegion = courseJpaEntity.secondaryRegion,
            ),
            categories = courseCategoryJpaEntities.map { CourseCategory.from(it.name) },
            name = courseJpaEntity.name,
            contents = courseJpaEntity.contents,
            views = courseJpaEntity.viewCount,
            comments = courseJpaEntity.commentCount,
            interests = courseJpaEntity.interestCount,
            writeDateTime = courseJpaEntity.createdAt,
        )

    fun toEntity(course: Course): CourseJpaEntity =
        CourseJpaEntity(
            id = course.id,
            userId = course.userId,
            name = course.name,
            primaryRegion = course.region.primaryRegion,
            secondaryRegion = course.region.secondaryRegion,
            contents = course.contents,
            viewCount = course.views,
            commentCount = course.comments,
            interestCount = course.interests,
        )
}

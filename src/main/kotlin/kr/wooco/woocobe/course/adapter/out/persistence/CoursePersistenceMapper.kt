package kr.wooco.woocobe.course.adapter.out.persistence

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseJpaEntity
import kr.wooco.woocobe.course.adapter.out.persistence.entity.CoursePlaceJpaEntity
import kr.wooco.woocobe.course.domain.entity.Course
import kr.wooco.woocobe.course.domain.vo.CourseCategory
import kr.wooco.woocobe.course.domain.vo.CoursePlace
import kr.wooco.woocobe.course.domain.vo.CourseRegion
import org.springframework.stereotype.Component

@Component
internal class CoursePersistenceMapper {
    fun toDomain(
        courseJpaEntity: CourseJpaEntity,
        coursePlaceJpaEntities: List<CoursePlaceJpaEntity>,
        courseCategoryJpaEntities: List<CourseCategoryJpaEntity>,
    ): Course =
        Course(
            id = courseJpaEntity.id,
            userId = courseJpaEntity.userId,
            region = CourseRegion(
                primaryRegion = courseJpaEntity.primaryRegion,
                secondaryRegion = courseJpaEntity.secondaryRegion,
            ),
            categories = courseCategoryJpaEntities.map { CourseCategory(it.name) },
            coursePlaces = coursePlaceJpaEntities.map { CoursePlace(order = it.order, placeId = it.placeId) },
            title = courseJpaEntity.title,
            contents = courseJpaEntity.contents,
            visitDate = courseJpaEntity.visitDate,
            views = courseJpaEntity.viewCount,
            comments = courseJpaEntity.commentCount,
            interests = courseJpaEntity.interestCount,
            writeDateTime = courseJpaEntity.createdAt,
        )

    fun toEntity(course: Course): CourseJpaEntity =
        CourseJpaEntity(
            id = course.id,
            userId = course.userId,
            title = course.title,
            primaryRegion = course.region.primaryRegion,
            secondaryRegion = course.region.secondaryRegion,
            contents = course.contents,
            visitDate = course.visitDate,
            viewCount = course.views,
            commentCount = course.comments,
            interestCount = course.interests,
        )
}

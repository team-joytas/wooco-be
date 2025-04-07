package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CoursePlace
import kr.wooco.woocobe.core.course.domain.vo.CourseRegion
import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CoursePlaceJpaEntity

internal object CoursePersistenceMapper {
    fun toDomainEntity(
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

    fun toJpaEntity(course: Course): CourseJpaEntity =
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

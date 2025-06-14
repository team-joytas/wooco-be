package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.dto.CourseView
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.entity.CoursePlace
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent
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
            places = coursePlaceJpaEntities.map { coursePlaceJpaEntity ->
                CoursePlace(
                    id = coursePlaceJpaEntity.id,
                    placeId = coursePlaceJpaEntity.placeId,
                    order = coursePlaceJpaEntity.order,
                )
            },
            content = CourseContent(
                title = courseJpaEntity.title,
                contents = courseJpaEntity.contents,
            ),
            categories = courseCategoryJpaEntities.map { CourseCategory(it.name) },
            visitDate = Course.VisitDate(courseJpaEntity.visitDate),
            status = Course.Status.valueOf(courseJpaEntity.status),
        )

    fun toReadModel(
        courseJpaEntity: CourseJpaEntity,
        coursePlaceJpaEntities: List<CoursePlaceJpaEntity>,
        courseCategoryJpaEntities: List<CourseCategoryJpaEntity>,
    ): CourseView =
        CourseView(
            id = courseJpaEntity.id,
            userId = courseJpaEntity.userId,
            title = courseJpaEntity.title,
            contents = courseJpaEntity.contents,
            primaryRegion = courseJpaEntity.primaryRegion,
            secondaryRegion = courseJpaEntity.secondaryRegion,
            categories = courseCategoryJpaEntities.map { it.name },
            visitDate = courseJpaEntity.visitDate,
            comments = courseJpaEntity.commentCount,
            likes = courseJpaEntity.likeCount,
            createdAt = courseJpaEntity.createdAt,
            coursePlaces = coursePlaceJpaEntities.map {
                CourseView.CoursePlaceView(
                    order = it.order,
                    placeId = it.placeId,
                )
            },
        )
}

package kr.wooco.woocobe.course.infrastructure.storage

import kr.wooco.woocobe.course.domain.model.CoursePlace
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseJpaEntity
import kr.wooco.woocobe.course.infrastructure.storage.entity.CoursePlaceJpaEntity
import org.springframework.stereotype.Component

@Component
class CoursePlaceStorageMapper {
    fun toDomain(coursePlaceJpaEntity: CoursePlaceJpaEntity): CoursePlace =
        CoursePlace(
            order = coursePlaceJpaEntity.order,
            placeId = coursePlaceJpaEntity.placeId,
        )

    fun toEntity(
        courseJpaEntity: CourseJpaEntity,
        coursePlace: CoursePlace,
    ): CoursePlaceJpaEntity =
        CoursePlaceJpaEntity(
            order = coursePlace.order,
            courseId = courseJpaEntity.id!!,
            placeId = coursePlace.placeId,
        )
}

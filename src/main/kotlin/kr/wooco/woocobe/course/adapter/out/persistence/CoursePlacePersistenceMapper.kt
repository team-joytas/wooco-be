package kr.wooco.woocobe.course.adapter.out.persistence

import kr.wooco.woocobe.course.adapter.out.persistence.entity.CourseJpaEntity
import kr.wooco.woocobe.course.adapter.out.persistence.entity.CoursePlaceJpaEntity
import kr.wooco.woocobe.course.domain.vo.CoursePlace
import org.springframework.stereotype.Component

@Component
internal class CoursePlacePersistenceMapper {
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
            courseId = courseJpaEntity.id,
            placeId = coursePlace.placeId,
        )
}

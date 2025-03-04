package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.domain.vo.CoursePlace
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CoursePlaceJpaEntity
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

package kr.wooco.woocobe.mysql.course.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.wooco.woocobe.core.course.application.port.out.query.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.query.InterestCourseSearchCondition
import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseLikeJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseMetaJpaEntity
import org.springframework.stereotype.Repository

// TODO: 영속성 상태 관리 고민해봐할듯 (ACTIVE, DELETED)
@Repository
class CourseCustomRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : CourseCustomRepository {
    override fun findAllCourseByCondition(condition: CourseSearchCondition): List<CourseJpaEntity> =
        executor
            .findAll(limit = condition.limit) {
                select(
                    entity(CourseJpaEntity::class),
                ).from(
                    entity(CourseJpaEntity::class),
                    leftJoin(CourseMetaJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(CourseMetaJpaEntity::id)),
                    ),
                    condition.category?.let {
                        leftJoin(CourseCategoryJpaEntity::class).on(
                            path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                        )
                    },
                ).whereAnd(
                    path(CourseJpaEntity::status).eq("ACTIVE"),
                    condition.writerId?.let {
                        path(CourseJpaEntity::userId).eq(it)
                    },
                    condition.primaryRegion?.let {
                        path(CourseJpaEntity::primaryRegion).eq(it)
                    },
                    condition.secondaryRegion?.let {
                        path(CourseJpaEntity::secondaryRegion).eq(it)
                    },
                    condition.category?.let {
                        path(CourseCategoryJpaEntity::name).eq(it)
                    },
                ).orderBy(
                    when (condition.sort) {
                        "POPULAR" -> path(CourseMetaJpaEntity::popularityScore).desc()
                        else -> path(CourseJpaEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()

    override fun findAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseJpaEntity> =
        executor
            .findAll(limit = condition.limit) {
                select(
                    entity(CourseJpaEntity::class),
                ).from(
                    entity(CourseJpaEntity::class),
                    leftJoin(CourseMetaJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(CourseMetaJpaEntity::id)),
                    ),
                    leftJoin(CourseLikeJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(CourseLikeJpaEntity::courseId)),
                    ),
                    condition.category?.let {
                        leftJoin(CourseCategoryJpaEntity::class).on(
                            path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                        )
                    },
                ).whereAnd(
                    path(CourseJpaEntity::status).eq("ACTIVE"),
                    path(CourseLikeJpaEntity::status).eq("ACTIVE"),
                    condition.targetUserId?.let {
                        path(CourseLikeJpaEntity::userId).eq(it)
                    },
                    condition.primaryRegion?.let {
                        path(CourseJpaEntity::primaryRegion).eq(it)
                    },
                    condition.secondaryRegion?.let {
                        path(CourseJpaEntity::secondaryRegion).eq(it)
                    },
                    condition.category?.let {
                        path(CourseCategoryJpaEntity::name).eq(it)
                    },
                ).orderBy(
                    when (condition.sort) {
                        "POPULAR" -> path(CourseMetaJpaEntity::popularityScore).desc()
                        else -> path(CourseJpaEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()
}

package kr.wooco.woocobe.mysql.course.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.wooco.woocobe.core.course.application.service.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.service.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import kr.wooco.woocobe.mysql.course.entity.InterestCourseJpaEntity
import org.springframework.stereotype.Repository

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
                    condition.category?.let {
                        leftJoin(CourseCategoryJpaEntity::class).on(
                            path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                        )
                    },
                ).whereAnd(
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
                        "POPULAR" -> path(CourseJpaEntity::interestCount).desc()
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
                    leftJoin(InterestCourseJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(InterestCourseJpaEntity::courseId)),
                    ),
                    condition.category?.let {
                        leftJoin(CourseCategoryJpaEntity::class).on(
                            path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                        )
                    },
                ).whereAnd(
                    condition.targetUserId?.let {
                        path(InterestCourseJpaEntity::userId).eq(it)
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
                        "POPULAR" -> path(CourseJpaEntity::interestCount).desc()
                        else -> path(CourseJpaEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()
}

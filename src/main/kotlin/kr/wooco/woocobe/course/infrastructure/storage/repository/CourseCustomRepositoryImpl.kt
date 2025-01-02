package kr.wooco.woocobe.course.infrastructure.storage.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseJpaEntity
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import org.springframework.stereotype.Repository

@Repository
class CourseCustomRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : CourseCustomRepository {
    override fun findAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<CourseJpaEntity> =
        executor
            .findAll {
                select(
                    entity(CourseJpaEntity::class),
                ).from(
                    entity(CourseJpaEntity::class),
                    leftJoin(CourseCategoryJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                    ),
                    leftJoin(UserEntity::class).on(
                        path(CourseJpaEntity::userId).eq(path(UserEntity::id)),
                    ),
                ).whereAnd(
                    path(CourseJpaEntity::userId).eq(path(UserEntity::id)),
                ).orderBy(
                    when (sort) {
                        CourseSortCondition.POPULAR -> path(CourseJpaEntity::viewCount).desc()
                        CourseSortCondition.RECENT -> path(CourseJpaEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()

    override fun findAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<CourseJpaEntity> =
        executor
            .findAll {
                select(
                    entity(CourseJpaEntity::class),
                ).from(
                    entity(CourseJpaEntity::class),
                    leftJoin(CourseCategoryJpaEntity::class).on(
                        path(CourseJpaEntity::id).eq(path(CourseCategoryJpaEntity::courseId)),
                    ),
                    leftJoin(UserEntity::class).on(
                        path(CourseJpaEntity::userId).eq(path(UserEntity::id)),
                    ),
                ).whereAnd(
                    path(CourseJpaEntity::primaryRegion).eq(region.primaryRegion),
                    path(CourseJpaEntity::secondaryRegion).eq(region.secondaryRegion),
                    path(CourseCategoryJpaEntity::name).eq(category),
                ).orderBy(
                    when (sort) {
                        CourseSortCondition.POPULAR -> path(CourseJpaEntity::viewCount).desc()
                        CourseSortCondition.RECENT -> path(CourseJpaEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()
}

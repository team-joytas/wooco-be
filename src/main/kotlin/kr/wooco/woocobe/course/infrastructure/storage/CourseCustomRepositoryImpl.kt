package kr.wooco.woocobe.course.infrastructure.storage

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import org.springframework.stereotype.Repository

@Repository
class CourseCustomRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : CourseCustomRepository {
    override fun findAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<CourseEntity> =
        executor
            .findAll {
                select(
                    entity(CourseEntity::class),
                ).from(
                    entity(CourseEntity::class),
                    leftJoin(CourseCategoryEntity::class).on(
                        path(CourseEntity::id).eq(path(CourseCategoryEntity::courseId)),
                    ),
                    leftJoin(UserEntity::class).on(
                        path(CourseEntity::userId).eq(path(UserEntity::id)),
                    ),
                ).whereAnd(
                    path(CourseEntity::userId).eq(path(UserEntity::id)),
                ).orderBy(
                    when (sort) {
                        CourseSortCondition.POPULAR -> path(CourseEntity::viewCount).desc()
                        CourseSortCondition.RECENT -> path(CourseEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()

    override fun findAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<CourseEntity> =
        executor
            .findAll {
                select(
                    entity(CourseEntity::class),
                ).from(
                    entity(CourseEntity::class),
                    leftJoin(CourseCategoryEntity::class).on(
                        path(CourseEntity::id).eq(path(CourseCategoryEntity::courseId)),
                    ),
                    leftJoin(UserEntity::class).on(
                        path(CourseEntity::userId).eq(path(UserEntity::id)),
                    ),
                ).whereAnd(
                    path(CourseEntity::primaryRegion).eq(region.primaryRegion),
                    path(CourseEntity::secondaryRegion).eq(region.secondaryRegion),
                    path(CourseCategoryEntity::name).eq(category),
                ).orderBy(
                    when (sort) {
                        CourseSortCondition.POPULAR -> path(CourseEntity::viewCount).desc()
                        CourseSortCondition.RECENT -> path(CourseEntity::createdAt).desc()
                    },
                )
            }.filterNotNull()
}

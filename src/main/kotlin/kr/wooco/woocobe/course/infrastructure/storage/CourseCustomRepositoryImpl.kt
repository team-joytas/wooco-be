package kr.wooco.woocobe.course.infrastructure.storage

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.user.infrastructure.storage.QUserEntity
import org.springframework.stereotype.Repository

@Repository
class CourseCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : CourseCustomRepository {
    override fun findAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<CourseEntity> =
        queryFactory
            .selectFrom(course)
            .leftJoin(courseCategory)
            .on(course.id.eq(courseCategory.courseId))
            .leftJoin(user)
            .on(course.userId.eq(user.id))
            .fetchJoin()
            .where(course.id.eq(userId))
            .orderBy(
                when (sort) {
                    CourseSortCondition.POPULAR -> course.viewCount.desc()
                    CourseSortCondition.RECENT -> course.createdAt.desc()
                },
            ).fetch()

    override fun findAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<CourseEntity> =
        queryFactory
            .selectFrom(course)
            .leftJoin(courseCategory)
            .on(course.id.eq(courseCategory.courseId))
            .leftJoin(user)
            .on(course.userId.eq(user.id))
            .fetchJoin()
            .where(
                course.primaryRegion.eq(region.primaryRegion),
                course.secondaryRegion.eq(region.secondaryRegion),
                courseCategory.name.eq(category),
            ).orderBy(
                when (sort) {
                    CourseSortCondition.POPULAR -> course.viewCount.desc()
                    CourseSortCondition.RECENT -> course.createdAt.desc()
                },
            ).fetch()

    companion object {
        private val user = QUserEntity.userEntity
        private val course = QCourseEntity.courseEntity
        private val courseCategory = QCourseCategoryEntity.courseCategoryEntity
    }
}

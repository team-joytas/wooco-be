package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseCategoryEntity
import kr.wooco.woocobe.course.infrastructure.storage.entity.CourseEntity
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseCategoryJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaCourseStorageGateway(
    private val courseJpaRepository: CourseJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
) : CourseStorageGateway {
    // FIXME: 업데이트 로직 분리
    override fun save(course: Course): Course {
        val courseEntity = courseJpaRepository.save(CourseEntity.from(course))
        if (course.id == 0L) {
            course.categories
                .map { CourseCategoryEntity.of(courseId = courseEntity.id!!, name = it.name) }
                .also(courseCategoryJpaRepository::saveAll)
        }
        return course
    }

    override fun getByCourseId(courseId: Long): Course? =
        courseJpaRepository.findByIdOrNull(courseId)?.let { courseEntity ->
            courseEntity.toDomain(
                courseCategory = courseCategoryJpaRepository
                    .findAllByCourseId(courseEntity.id!!)
                    .map { it.toDomain() },
            )
        }

    override fun getAllByCourseIds(courseIds: List<Long>): List<Course> {
        TODO("Not yet implemented")
    }

    override fun getAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<Course> =
        courseJpaRepository
            .findAllByRegionAndCategoryWithSort(region = region, category = category, sort = sort)
            .run {
                val categoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(map { it.id!! })
                map { courseEntity ->
                    courseEntity.toDomain(
                        courseCategory = categoryEntities
                            .filter { courseEntity.userId == it.courseId }
                            .map { it.toDomain() },
                    )
                }
            }

    override fun getAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<Course> =
        courseJpaRepository
            .findAllByUserIdWithSort(userId = userId, sort = sort)
            .run {
                val categoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(map { it.id!! })
                map { courseEntity ->
                    courseEntity.toDomain(
                        courseCategory = categoryEntities
                            .filter { courseEntity.userId == it.courseId }
                            .map { it.toDomain() },
                    )
                }
            }

    override fun deleteByCourseId(courseId: Long) = courseJpaRepository.deleteById(courseId)
}

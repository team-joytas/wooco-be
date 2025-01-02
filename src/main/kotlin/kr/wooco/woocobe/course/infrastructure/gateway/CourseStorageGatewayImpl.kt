package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.CourseCategoryStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.CourseStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseCategoryJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CourseStorageGatewayImpl(
    private val courseJpaRepository: CourseJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
    private val courseStorageMapper: CourseStorageMapper,
    private val courseCategoryStorageMapper: CourseCategoryStorageMapper,
) : CourseStorageGateway {
    // FIXME: 업데이트 로직 분리
    override fun save(course: Course): Course {
        val courseEntity = courseStorageMapper.toEntity(course)
        courseJpaRepository.save(courseEntity)
        courseCategoryJpaRepository.deleteAllByCourseId(courseEntity.id!!)
        val courseCategoryEntities = course.categories.map { courseCategoryStorageMapper.toEntity(courseEntity, it) }
        courseCategoryJpaRepository.saveAll(courseCategoryEntities)
        return courseStorageMapper.toDomain(courseEntity, courseCategoryEntities)
    }

    override fun getByCourseId(courseId: Long): Course {
        val courseEntity = courseJpaRepository.findByIdOrNull(courseId)
            ?: throw RuntimeException()
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseEntity.id!!)
        return courseStorageMapper.toDomain(courseEntity, courseCategoryEntities)
    }

    override fun getAllByCourseIds(courseIds: List<Long>): List<Course> {
        val courseEntities = courseJpaRepository.findAllById(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseIds)
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun getAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String,
        sort: CourseSortCondition,
    ): List<Course> {
        val courseEntities =
            courseJpaRepository.findAllByRegionAndCategoryWithSort(region = region, category = category, sort = sort)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id!! })
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun getAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<Course> {
        val courseEntities = courseJpaRepository.findAllByUserIdWithSort(userId = userId, sort = sort)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id!! })
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun deleteByCourseId(courseId: Long) {
        courseJpaRepository.deleteById(courseId)
        courseCategoryJpaRepository.deleteAllByCourseId(courseId)
    }
}

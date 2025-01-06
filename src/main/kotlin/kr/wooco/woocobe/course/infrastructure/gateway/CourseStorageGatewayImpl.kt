package kr.wooco.woocobe.course.infrastructure.gateway

import kr.wooco.woocobe.course.domain.gateway.CourseStorageGateway
import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.course.domain.model.CourseRegion
import kr.wooco.woocobe.course.domain.model.CourseSortCondition
import kr.wooco.woocobe.course.infrastructure.storage.CourseCategoryStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.CoursePlaceStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.CourseStorageMapper
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseCategoryJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.repository.CourseJpaRepository
import kr.wooco.woocobe.course.infrastructure.storage.repository.CoursePlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
@Suppress("Duplicates")
internal class CourseStorageGatewayImpl(
    private val courseJpaRepository: CourseJpaRepository,
    private val coursePlaceJpaRepository: CoursePlaceJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
    private val courseStorageMapper: CourseStorageMapper,
    private val coursePlaceStorageMapper: CoursePlaceStorageMapper,
    private val courseCategoryStorageMapper: CourseCategoryStorageMapper,
) : CourseStorageGateway {
    // FIXME: 업데이트 로직 분리 (저장할 때마다 카테고리들과 코스 장소를 전부 삭제했다가 다시 저장함)
    override fun save(course: Course): Course {
        val courseEntity = courseStorageMapper.toEntity(course)
        courseJpaRepository.save(courseEntity)

        coursePlaceJpaRepository.deleteAllByCourseId(courseEntity.id!!)
        val coursePlaceEntities = course.coursePlaces.map { coursePlaceStorageMapper.toEntity(courseEntity, it) }
        coursePlaceJpaRepository.saveAll(coursePlaceEntities)

        courseCategoryJpaRepository.deleteAllByCourseId(courseEntity.id!!)
        val courseCategoryEntities = course.categories.map { courseCategoryStorageMapper.toEntity(courseEntity, it) }
        courseCategoryJpaRepository.saveAll(courseCategoryEntities)

        return courseStorageMapper.toDomain(courseEntity, coursePlaceEntities, courseCategoryEntities)
    }

    override fun getByCourseId(courseId: Long): Course {
        val courseEntity = courseJpaRepository.findByIdOrNull(courseId)
            ?: throw RuntimeException()
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseEntity.id!!)
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseId(courseId)
        return courseStorageMapper.toDomain(courseEntity, coursePlaceEntities, courseCategoryEntities)
    }

    override fun getAllByCourseIds(courseIds: List<Long>): List<Course> {
        val courseEntities = courseJpaRepository.findAllById(courseIds)
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseIds)
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun getAllByRegionAndCategoryWithSort(
        region: CourseRegion,
        category: String?,
        sort: CourseSortCondition,
    ): List<Course> {
        val courseEntities =
            courseJpaRepository.findAllByRegionAndCategoryWithSort(region = region, category = category, sort = sort)
        val courseIds = courseEntities.map { it.id!! }
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id!! })
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun getAllByUserIdWithSort(
        userId: Long,
        sort: CourseSortCondition,
    ): List<Course> {
        val courseEntities = courseJpaRepository.findAllByUserIdWithSort(userId = userId, sort = sort)
        val courseIds = courseEntities.map { it.id!! }
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id!! })
        return courseEntities.map { courseJpaEntity ->
            courseStorageMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
                coursePlaceJpaEntities = coursePlaceEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun deleteByCourseId(courseId: Long) {
        courseJpaRepository.deleteById(courseId)
        coursePlaceJpaRepository.deleteAllByCourseId(courseId)
        courseCategoryJpaRepository.deleteAllByCourseId(courseId)
    }
}

package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.application.port.out.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import kr.wooco.woocobe.mysql.course.repository.CourseCategoryJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CourseJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CoursePlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class CoursePersistenceAdapter(
    private val courseJpaRepository: CourseJpaRepository,
    private val coursePlaceJpaRepository: CoursePlaceJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
) : CourseQueryPort,
    CourseCommandPort {
    override fun getByCourseId(courseId: Long): Course {
        val courseJpaEntity = courseJpaRepository.findByIdOrNull(courseId)
            ?: throw NotExistsCourseException
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseJpaEntity.id)
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseId(courseId)
        return CoursePersistenceMapper.toDomainEntity(courseJpaEntity, coursePlaceEntities, courseCategoryEntities)
    }

    @Suppress("Duplicates") // TODO: JDSL
    override fun getAllCourseByCondition(condition: CourseSearchCondition): List<Course> {
        val courseJpaEntities = courseJpaRepository.findAllCourseByCondition(condition)
        val courseIds = courseJpaEntities.map { it.id }
        val coursePlaceJpaEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryJpaEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseJpaEntities.map { it.id })
        return courseJpaEntities.map { courseJpaEntity ->
            CoursePersistenceMapper.toDomainEntity(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceJpaEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryJpaEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    @Suppress("Duplicates") // TODO: JDSL
    override fun getAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<Course> {
        val courseJpaEntities = courseJpaRepository.findAllInterestCourseByCondition(condition)
        val courseIds = courseJpaEntities.map { it.id }
        val coursePlaceJpaEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryJpaEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseJpaEntities.map { it.id })
        return courseJpaEntities.map { courseJpaEntity ->
            CoursePersistenceMapper.toDomainEntity(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceJpaEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryJpaEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun countByUserId(userId: Long): Long = courseJpaRepository.countByUserId(userId)

    override fun saveCourse(course: Course): Course {
        val courseJpaEntity = CoursePersistenceMapper.toJpaEntity(course)
        courseJpaRepository.save(courseJpaEntity)

        coursePlaceJpaRepository.deleteAllByCourseId(courseJpaEntity.id)
        val coursePlaceJpaEntities =
            course.coursePlaces.map { CoursePlacePersistenceMapper.toEntity(courseJpaEntity, it) }
        coursePlaceJpaRepository.saveAll(coursePlaceJpaEntities)

        courseCategoryJpaRepository.deleteAllByCourseId(courseJpaEntity.id)
        val courseCategoryJpaEntities =
            course.categories.map { CourseCategoryPersistenceMapper.toJpaEntity(courseJpaEntity, it) }
        courseCategoryJpaRepository.saveAll(courseCategoryJpaEntities)

        return CoursePersistenceMapper.toDomainEntity(
            courseJpaEntity,
            coursePlaceJpaEntities,
            courseCategoryJpaEntities,
        )
    }

    override fun deleteByCourseId(courseId: Long) {
        courseJpaRepository.deleteById(courseId)
        coursePlaceJpaRepository.deleteAllByCourseId(courseId)
        courseCategoryJpaRepository.deleteAllByCourseId(courseId)
    }
}

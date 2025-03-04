package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.DeleteCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.LoadCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.SaveCoursePersistencePort
import kr.wooco.woocobe.core.course.application.service.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.service.dto.InterestCourseSearchCondition
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
    private val coursePersistenceMapper: CoursePersistenceMapper,
    private val coursePlacePersistenceMapper: CoursePlacePersistenceMapper,
    private val courseCategoryPersistenceMapper: CourseCategoryPersistenceMapper,
) : LoadCoursePersistencePort,
    SaveCoursePersistencePort,
    DeleteCoursePersistencePort {
    override fun getByCourseId(courseId: Long): Course {
        val courseEntity = courseJpaRepository.findByIdOrNull(courseId)
            ?: throw NotExistsCourseException
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseEntity.id)
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseId(courseId)
        return coursePersistenceMapper.toDomain(courseEntity, coursePlaceEntities, courseCategoryEntities)
    }

    @Suppress("Duplicates") // TODO: JDSL
    override fun getAllCourseByCondition(condition: CourseSearchCondition): List<Course> {
        val courseEntities = courseJpaRepository.findAllCourseByCondition(condition)
        val courseIds = courseEntities.map { it.id }
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id })
        return courseEntities.map { courseJpaEntity ->
            coursePersistenceMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    @Suppress("Duplicates") // TODO: JDSL
    override fun getAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<Course> {
        val courseEntities = courseJpaRepository.findAllInterestCourseByCondition(condition)
        val courseIds = courseEntities.map { it.id }
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseEntities.map { it.id })
        return courseEntities.map { courseJpaEntity ->
            coursePersistenceMapper.toDomain(
                courseJpaEntity = courseJpaEntity,
                coursePlaceJpaEntities = coursePlaceEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    override fun saveCourse(course: Course): Course {
        val courseEntity = coursePersistenceMapper.toEntity(course)
        courseJpaRepository.save(courseEntity)

        coursePlaceJpaRepository.deleteAllByCourseId(courseEntity.id)
        val coursePlaceEntities = course.coursePlaces.map { coursePlacePersistenceMapper.toEntity(courseEntity, it) }
        coursePlaceJpaRepository.saveAll(coursePlaceEntities)

        courseCategoryJpaRepository.deleteAllByCourseId(courseEntity.id)
        val courseCategoryEntities =
            course.categories.map { courseCategoryPersistenceMapper.toEntity(courseEntity, it) }
        courseCategoryJpaRepository.saveAll(courseCategoryEntities)

        return coursePersistenceMapper.toDomain(courseEntity, coursePlaceEntities, courseCategoryEntities)
    }

    override fun deleteByCourseId(courseId: Long) {
        courseJpaRepository.deleteById(courseId)
        coursePlaceJpaRepository.deleteAllByCourseId(courseId)
        courseCategoryJpaRepository.deleteAllByCourseId(courseId)
    }
}

package kr.wooco.woocobe.mysql.course

import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.application.port.out.query.CourseCommentTarget
import kr.wooco.woocobe.core.course.application.port.out.query.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.query.CourseView
import kr.wooco.woocobe.core.course.application.port.out.query.InterestCourseSearchCondition
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import kr.wooco.woocobe.mysql.course.entity.CourseCategoryJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import kr.wooco.woocobe.mysql.course.entity.CoursePlaceJpaEntity
import kr.wooco.woocobe.mysql.course.repository.CourseCategoryJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CourseJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CourseMetaJpaRepository
import kr.wooco.woocobe.mysql.course.repository.CoursePlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class CoursePersistenceAdapter(
    private val courseJpaRepository: CourseJpaRepository,
    private val courseMetaJpaRepository: CourseMetaJpaRepository,
    private val coursePlaceJpaRepository: CoursePlaceJpaRepository,
    private val courseCategoryJpaRepository: CourseCategoryJpaRepository,
) : CourseQueryPort,
    CourseCommandPort {
    override fun getByCourseId(courseId: Long): Course {
        val courseJpaEntity = courseJpaRepository.findByIdOrNull(courseId)
            ?: throw NotExistsCourseException
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseJpaEntity.id)
        val coursePlaceJpaEntities = coursePlaceJpaRepository.findAllByCourseId(courseJpaEntity.id)
        return CoursePersistenceMapper.toDomainEntity(courseJpaEntity, coursePlaceJpaEntities, courseCategoryEntities)
    }

    override fun getViewByCourseId(courseId: Long): CourseView {
        val courseJpaEntity = courseJpaRepository.findByCourseIdWithActiveOrNull(courseId)
            ?: throw NotExistsCourseException
        val courseMetaJpaEntity = courseMetaJpaRepository.findByIdOrNull(courseId)
            ?: throw NotExistsCourseException
        val courseCategoryEntities = courseCategoryJpaRepository.findAllByCourseId(courseJpaEntity.id)
        val coursePlaceEntities = coursePlaceJpaRepository.findAllByCourseId(courseJpaEntity.id)
        return CoursePersistenceMapper.toReadModel(courseJpaEntity, courseMetaJpaEntity, coursePlaceEntities, courseCategoryEntities)
    }

    override fun getViewAllCourseByCondition(condition: CourseSearchCondition): List<CourseView> {
        val courseJpaEntities = courseJpaRepository.findAllCourseByCondition(condition)
        return convertCourses(courseJpaEntities)
    }

    override fun getAllViewInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseView> {
        val courseJpaEntities = courseJpaRepository.findAllInterestCourseByCondition(condition)
        return convertCourses(courseJpaEntities)
    }

    override fun countByUserId(userId: Long): Long = courseJpaRepository.countByUserId(userId)

    override fun existsByCourseId(courseId: Long): Boolean = courseJpaRepository.existsById(courseId)

    override fun existsActiveByCourseId(courseId: Long): Boolean = courseJpaRepository.existsByCourseIdAndActive(courseId)

    override fun getActiveCommentTargetByCourseId(courseId: Long): CourseCommentTarget {
        val courseJpaEntity = courseJpaRepository.findByCourseIdWithActiveOrNull(courseId)
            ?: throw NotExistsCourseException
        return CourseCommentTarget(
            title = courseJpaEntity.title,
            writerId = courseJpaEntity.userId,
        )
    }

    private fun convertCourses(courseJpaEntities: List<CourseJpaEntity>): List<CourseView> {
        val courseIds = courseJpaEntities.map { it.id }
        val courseMetaJpaEntities = courseMetaJpaRepository.findAllByIdIn(courseIds)
        val courseMetaJpaEntityMap = courseMetaJpaEntities.associateBy { it.id }
        val coursePlaceJpaEntities = coursePlaceJpaRepository.findAllByCourseIdIn(courseIds)
        val courseCategoryJpaEntities = courseCategoryJpaRepository.findAllByCourseIdIn(courseIds)

        return courseJpaEntities.map { courseJpaEntity ->
            CoursePersistenceMapper.toReadModel(
                courseJpaEntity = courseJpaEntity,
                courseMetaJpaEntity = courseMetaJpaEntityMap[courseJpaEntity.id] ?: throw NotExistsCourseException,
                coursePlaceJpaEntities = coursePlaceJpaEntities.filter { it.courseId == courseJpaEntity.id },
                courseCategoryJpaEntities = courseCategoryJpaEntities.filter { it.courseId == courseJpaEntity.id },
            )
        }
    }

    // FIXME: 개선 필요
    @Transactional
    override fun saveCourse(course: Course): Long =
        if (course.id == 0L) {
            createNew(course)
        } else {
            updateCourse(course)
        }

    fun createNew(course: Course): Long {
        val courseJpaEntity = courseJpaRepository.save(CourseJpaEntity.create(course))

        val courseCategoryJpaEntities = CourseCategoryJpaEntity.listOf(course, courseJpaEntity)
        courseCategoryJpaRepository.saveAll(courseCategoryJpaEntities)

        val coursePlaceJpaEntities = CoursePlaceJpaEntity.listOf(course, courseJpaEntity)
        coursePlaceJpaRepository.saveAll(coursePlaceJpaEntities)

        return courseJpaEntity.id
    }

    fun updateCourse(course: Course): Long {
        val courseJpaEntity = courseJpaRepository.findByIdOrNull(course.id)!!.let {
            courseJpaRepository.save(it.applyUpdate(course))
        }

        val courseCategoryJpaEntities = CourseCategoryJpaEntity.listOf(course, courseJpaEntity)
        syncCourseCategoryJpaEntities(courseJpaEntity.id, courseCategoryJpaEntities)

        val coursePlaceJpaEntities = CoursePlaceJpaEntity.listOf(course, courseJpaEntity)
        syncCoursePlaceJpaEntities(courseJpaEntity.id, coursePlaceJpaEntities)

        return courseJpaEntity.id
    }

    private fun syncCourseCategoryJpaEntities(
        courseId: Long,
        courseCategoryJpaEntities: List<CourseCategoryJpaEntity>,
    ) {
        val unusedCourseCategoryJpaEntities =
            courseCategoryJpaRepository.findAllByCourseId(courseId) -
                courseCategoryJpaRepository.saveAll(courseCategoryJpaEntities).toSet()

        if (unusedCourseCategoryJpaEntities.isNotEmpty()) {
            courseCategoryJpaRepository.deleteAllInBatch(unusedCourseCategoryJpaEntities)
        }
    }

    private fun syncCoursePlaceJpaEntities(
        courseId: Long,
        coursePlaceJpaEntities: List<CoursePlaceJpaEntity>,
    ) {
        val unusedCoursePlaceJpaEntities =
            coursePlaceJpaRepository.findAllByCourseId(courseId) - coursePlaceJpaEntities.toSet()

        coursePlaceJpaRepository.saveAll(coursePlaceJpaEntities)

        if (unusedCoursePlaceJpaEntities.isNotEmpty()) {
            coursePlaceJpaRepository.deleteAllInBatch(unusedCoursePlaceJpaEntities)
        }
    }
}

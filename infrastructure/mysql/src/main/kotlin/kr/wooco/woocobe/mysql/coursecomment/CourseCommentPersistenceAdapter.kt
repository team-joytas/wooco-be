package kr.wooco.woocobe.mysql.coursecomment

import kr.wooco.woocobe.core.coursecomment.application.port.out.DeleteCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.LoadCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.application.port.out.SaveCourseCommentPersistencePort
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment
import kr.wooco.woocobe.core.coursecomment.domain.exception.NotExistsCommentException
import kr.wooco.woocobe.mysql.coursecomment.repository.CourseCommentJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class CourseCommentPersistenceAdapter(
    private val courseCommentJpaRepository: CourseCommentJpaRepository,
    private val courseCommentPersistenceMapper: CourseCommentPersistenceMapper,
) : LoadCourseCommentPersistencePort,
    SaveCourseCommentPersistencePort,
    DeleteCourseCommentPersistencePort {
    override fun getByCourseCommentId(courseCommentId: Long): CourseComment {
        val courseCommentJpaEntity = courseCommentJpaRepository.findByIdOrNull(courseCommentId)
            ?: throw NotExistsCommentException
        return courseCommentPersistenceMapper.toDomain(courseCommentJpaEntity)
    }

    override fun getAllByCourseId(courseId: Long): List<CourseComment> {
        val courseCommentJpaEntities = courseCommentJpaRepository.findAllByCourseId(courseId)
        return courseCommentJpaEntities.map { courseCommentPersistenceMapper.toDomain(it) }
    }

    override fun saveCourseComment(courseComment: CourseComment): CourseComment {
        val courseCommentJpaEntity = courseCommentPersistenceMapper.toEntity(courseComment)
        courseCommentJpaRepository.save(courseCommentJpaEntity)
        return courseCommentPersistenceMapper.toDomain(courseCommentJpaEntity)
    }

    override fun deleteByCourseCommentId(courseCommentId: Long) {
        courseCommentJpaRepository.deleteById(courseCommentId)
    }
}

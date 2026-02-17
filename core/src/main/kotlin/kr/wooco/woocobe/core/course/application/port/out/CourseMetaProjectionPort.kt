package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import java.time.LocalDateTime

interface CourseMetaProjectionPort {
    fun getByCourseId(courseId: Long): CourseMetaProjection

    fun save(courseMetaProjection: CourseMetaProjection)

    fun getCreatedAtByCourseId(courseId: Long): LocalDateTime
}

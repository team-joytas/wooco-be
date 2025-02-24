package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.application.service.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.service.dto.InterestCourseSearchCondition
import kr.wooco.woocobe.core.course.domain.entity.Course

interface LoadCoursePersistencePort {
    fun getByCourseId(courseId: Long): Course

    // TODO: Read model 분리
    fun getAllCourseByCondition(condition: CourseSearchCondition): List<Course>

    fun getAllInterestCourseByCondition(condition: InterestCourseSearchCondition): List<Course>
}

package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.domain.entity.Course

interface SaveCoursePersistencePort {
    fun saveCourse(course: Course): Course
}

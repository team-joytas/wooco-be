package kr.wooco.woocobe.course.application.port.out

import kr.wooco.woocobe.course.domain.entity.Course

interface SaveCoursePersistencePort {
    fun saveCourse(course: Course): Course
}

package kr.wooco.woocobe.course.domain.gateway

import kr.wooco.woocobe.course.domain.model.Course

interface CourseStorageGateway {
    fun save(course: Course): Course

    fun getByCourseId(courseId: Long): Course?
}

package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.domain.entity.CourseLike

interface CourseLikeCommandPort {
    fun getByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): CourseLike

    fun getOrNullByUserIdAndCourseId(
        userId: Long,
        courseId: Long,
    ): CourseLike?

    fun saveLikeCourse(courseLike: CourseLike): Long
}

package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.application.port.out.dto.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.dto.CourseView
import kr.wooco.woocobe.core.course.application.port.out.dto.InterestCourseSearchCondition

// TODO: 네이밍 변경(View prefix 제거) & adapter 분리(command 와 query) 해야할듯?
interface CourseQueryPort {
    fun getViewByCourseId(courseId: Long): CourseView

    fun getViewAllCourseByCondition(condition: CourseSearchCondition): List<CourseView>

    fun getAllViewInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseView>

    fun countByUserId(userId: Long): Long

    fun existsByCourseId(courseId: Long): Boolean

    // Projection 전용
    fun increaseComments(courseId: Long)

    fun decreaseComments(courseId: Long)

    fun increaseLikes(courseId: Long)

    fun decreaseLikes(courseId: Long)
}

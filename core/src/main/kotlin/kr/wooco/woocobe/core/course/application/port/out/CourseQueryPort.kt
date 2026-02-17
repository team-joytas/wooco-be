package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.application.port.out.query.CourseCommentTarget
import kr.wooco.woocobe.core.course.application.port.out.query.CourseSearchCondition
import kr.wooco.woocobe.core.course.application.port.out.query.CourseView
import kr.wooco.woocobe.core.course.application.port.out.query.InterestCourseSearchCondition

// TODO: 네이밍 변경(View prefix 제거) & adapter 분리(command 와 query) 해야할듯?
interface CourseQueryPort {
    fun getViewByCourseId(courseId: Long): CourseView

    fun getViewAllCourseByCondition(condition: CourseSearchCondition): List<CourseView>

    fun getAllViewInterestCourseByCondition(condition: InterestCourseSearchCondition): List<CourseView>

    fun countByUserId(userId: Long): Long

    fun existsByCourseId(courseId: Long): Boolean

    fun existsActiveByCourseId(courseId: Long): Boolean

    fun getActiveCommentTargetByCourseId(courseId: Long): CourseCommentTarget
}

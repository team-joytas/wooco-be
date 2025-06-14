package kr.wooco.woocobe.core.course.domain.command

import kr.wooco.woocobe.core.course.domain.entity.Course.VisitDate
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent

data class UpdateCourseInfoCommand(
    val userId: Long,
    val content: CourseContent,
    val visitDate: VisitDate,
    val placeIds: List<Long>,
    val categories: List<CourseCategory>,
)

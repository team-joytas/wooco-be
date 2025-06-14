package kr.wooco.woocobe.core.course.domain.command

import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent
import kr.wooco.woocobe.core.course.domain.vo.CourseRegion

data class CreateCourseCommand(
    val userId: Long,
    val region: CourseRegion,
    val content: CourseContent,
    val placeIds: List<Long>,
    val visitDate: Course.VisitDate,
    val categories: List<CourseCategory>,
)

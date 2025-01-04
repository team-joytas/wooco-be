package kr.wooco.woocobe.course.ui.web.controller

import kr.wooco.woocobe.course.ui.web.facade.CourseFacadeService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO: 구현

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(
    private val courseFacadeService: CourseFacadeService,
) : CourseApiController

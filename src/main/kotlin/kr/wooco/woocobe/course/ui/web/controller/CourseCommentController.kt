package kr.wooco.woocobe.course.ui.web.controller

import kr.wooco.woocobe.course.ui.web.controller.request.CreateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.request.UpdateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.response.CourseCommentDetailResponse
import kr.wooco.woocobe.course.ui.web.controller.response.CreateCourseCommentResponse
import kr.wooco.woocobe.course.ui.web.facade.CourseCommentCommandFacade
import kr.wooco.woocobe.course.ui.web.facade.CourseCommentQueryFacade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/comments")
class CourseCommentController(
    private val courseCommentQueryFacade: CourseCommentQueryFacade,
    private val courseCommentCommandFacade: CourseCommentCommandFacade,
) : CourseCommentApi {
    @GetMapping("/courses/{courseId}")
    override fun getAllCourseComment(
        @PathVariable courseId: Long,
    ): ResponseEntity<List<CourseCommentDetailResponse>> {
        val response = courseCommentQueryFacade.getAllCourseComment(courseId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/courses/{courseId}")
    override fun createCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: CreateCourseCommentRequest,
    ): ResponseEntity<CreateCourseCommentResponse> {
        val response =
            courseCommentCommandFacade.createCourseComment(userId = userId, courseId = courseId, request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PatchMapping("/{commentId}")
    override fun updateCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCourseCommentRequest,
    ): ResponseEntity<Unit> {
        courseCommentCommandFacade.updateCourseComment(userId = userId, commentId = commentId, request = request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{commentId}")
    override fun deleteCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        courseCommentCommandFacade.deleteCourseComment(userId = userId, commentId = commentId)
        return ResponseEntity.ok().build()
    }
}

package kr.wooco.woocobe.course.ui.web.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.course.ui.web.controller.request.CreateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.request.UpdateCourseCommentRequest
import kr.wooco.woocobe.course.ui.web.controller.response.CourseCommentDetailResponse
import kr.wooco.woocobe.course.ui.web.controller.response.CreateCourseCommentResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "코스 댓글 API")
interface CourseCommentApi {
    fun getAllCourseComment(
        @PathVariable courseId: Long,
    ): ResponseEntity<List<CourseCommentDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun createCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: CreateCourseCommentRequest,
    ): ResponseEntity<CreateCourseCommentResponse>

    @SecurityRequirement(name = "JWT")
    fun updateCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCourseCommentRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun deleteCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit>
}

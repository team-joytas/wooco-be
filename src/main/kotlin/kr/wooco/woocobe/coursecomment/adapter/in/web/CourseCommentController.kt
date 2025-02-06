package kr.wooco.woocobe.coursecomment.adapter.`in`.web

import kr.wooco.woocobe.coursecomment.adapter.`in`.web.request.CreateCourseCommentRequest
import kr.wooco.woocobe.coursecomment.adapter.`in`.web.request.UpdateCourseCommentRequest
import kr.wooco.woocobe.coursecomment.adapter.`in`.web.response.CourseCommentDetailResponse
import kr.wooco.woocobe.coursecomment.adapter.`in`.web.response.CreateCourseCommentResponse
import kr.wooco.woocobe.coursecomment.application.port.`in`.CreateCourseCommentUseCase
import kr.wooco.woocobe.coursecomment.application.port.`in`.DeleteCourseCommentUseCase
import kr.wooco.woocobe.coursecomment.application.port.`in`.ReadAllCourseCommentUseCase
import kr.wooco.woocobe.coursecomment.application.port.`in`.UpdateCourseCommentUseCase
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
    private val readAllCourseCommentUseCase: ReadAllCourseCommentUseCase,
    private val createCourseCommentUseCase: CreateCourseCommentUseCase,
    private val updateCourseCommentUseCase: UpdateCourseCommentUseCase,
    private val deleteCourseCommentUseCase: DeleteCourseCommentUseCase,
) : CourseCommentApi {
    @GetMapping("/courses/{courseId}")
    override fun getAllCourseComment(
        @PathVariable courseId: Long,
    ): ResponseEntity<List<CourseCommentDetailResponse>> {
        val query = ReadAllCourseCommentUseCase.Query(courseId)
        val results = readAllCourseCommentUseCase.readAllCourseComment(query)
        return ResponseEntity.ok(CourseCommentDetailResponse.listFrom(results))
    }

    @PostMapping("/courses/{courseId}")
    override fun createCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: CreateCourseCommentRequest,
    ): ResponseEntity<CreateCourseCommentResponse> {
        val command = request.toCommand(userId, courseId)
        val results = createCourseCommentUseCase.createCourseComment(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCourseCommentResponse(results))
    }

    @PatchMapping("/{commentId}")
    override fun updateCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: UpdateCourseCommentRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId, commentId)
        updateCourseCommentUseCase.updateCourseComment(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{commentId}")
    override fun deleteCourseComment(
        @AuthenticationPrincipal userId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        val command = DeleteCourseCommentUseCase.Command(userId, commentId)
        deleteCourseCommentUseCase.deleteCourseComment(command)
        return ResponseEntity.ok().build()
    }
}

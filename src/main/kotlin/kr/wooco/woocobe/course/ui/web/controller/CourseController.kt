package kr.wooco.woocobe.course.ui.web.controller

import kr.wooco.woocobe.course.ui.web.controller.request.CreateCourseRequest
import kr.wooco.woocobe.course.ui.web.controller.request.UpdateCourseRequest
import kr.wooco.woocobe.course.ui.web.controller.response.CourseDetailResponse
import kr.wooco.woocobe.course.ui.web.controller.response.CreateCourseResponse
import kr.wooco.woocobe.course.ui.web.facade.CourseCommandFacade
import kr.wooco.woocobe.course.ui.web.facade.CourseQueryFacade
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(
    private val courseQueryFacade: CourseQueryFacade,
    private val courseCommandFacade: CourseCommandFacade,
) : CourseApi {
    @GetMapping("/{courseId}")
    override fun getCourseDetail(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable courseId: Long,
    ): ResponseEntity<CourseDetailResponse> {
        val response = courseQueryFacade.getCourseDetail(userId = userId, courseId = courseId)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    override fun getAllCourse(
        @AuthenticationPrincipal userId: Long?,
        @RequestParam(required = false, defaultValue = "RECENT") sort: String,
        @RequestParam(required = false) primaryRegion: String?,
        @RequestParam(required = false) secondaryRegion: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) limit: Int?,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val response = courseQueryFacade.getAllCourseDetail(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            category = category,
            limit = limit,
            sort = sort,
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}")
    override fun getAllUserCourse(
        @AuthenticationPrincipal currentUserId: Long?,
        @PathVariable userId: Long,
        @RequestParam(required = false, defaultValue = "RECENT") sort: String,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val response =
            courseQueryFacade.getAllUserCourseDetail(currentUserId = currentUserId, userId = userId, sort = sort)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}/like")
    override fun getAllUserInterestCourse(
        @AuthenticationPrincipal currentUserId: Long?,
        @PathVariable userId: Long,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val response = courseQueryFacade.getAllUserInterestCourse(currentUserId = currentUserId, userId = userId)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    override fun createCourse(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateCourseRequest,
    ): ResponseEntity<CreateCourseResponse> {
        val response = courseCommandFacade.createCourse(userId = userId, request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/{courseId}/like")
    override fun addCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        courseCommandFacade.addInterestCourse(userId = userId, courseId = courseId)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{courseId}")
    override fun updateCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: UpdateCourseRequest,
    ): ResponseEntity<Unit> {
        courseCommandFacade.updateCourse(userId = userId, courseId = courseId, request = request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{courseId}")
    override fun deleteCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        courseCommandFacade.deleteCourse(userId = userId, courseId = courseId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{courseId}/like")
    override fun deleteCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        courseCommandFacade.deleteInterestCourse(userId = userId, courseId = courseId)
        return ResponseEntity.ok().build()
    }
}

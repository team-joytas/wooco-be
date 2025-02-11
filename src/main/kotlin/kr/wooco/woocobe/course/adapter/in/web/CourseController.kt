package kr.wooco.woocobe.course.adapter.`in`.web

import kr.wooco.woocobe.course.adapter.`in`.web.request.CreateCourseRequest
import kr.wooco.woocobe.course.adapter.`in`.web.request.UpdateCourseRequest
import kr.wooco.woocobe.course.adapter.`in`.web.response.CourseDetailResponse
import kr.wooco.woocobe.course.adapter.`in`.web.response.CreateCourseResponse
import kr.wooco.woocobe.course.application.port.`in`.CreateCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.CreateInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.DeleteCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.DeleteInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.ReadAllCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.ReadAllInterestCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.ReadCourseUseCase
import kr.wooco.woocobe.course.application.port.`in`.UpdateCourseUseCase
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
    private val readCourseUseCase: ReadCourseUseCase,
    private val createCourseUseCase: CreateCourseUseCase,
    private val updateCourseUseCase: UpdateCourseUseCase,
    private val deleteCourseUseCase: DeleteCourseUseCase,
    private val readAllCourseUseCase: ReadAllCourseUseCase,
    private val createInterestCourseUseCase: CreateInterestCourseUseCase,
    private val deleteInterestCourseUseCase: DeleteInterestCourseUseCase,
    private val readAllInterestCourseUseCase: ReadAllInterestCourseUseCase,
) : CourseApi {
    @GetMapping("/{courseId}")
    override fun getCourseDetail(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable courseId: Long,
    ): ResponseEntity<CourseDetailResponse> {
        val query = ReadCourseUseCase.Query(userId, courseId)
        val results = readCourseUseCase.readCourse(query)
        return ResponseEntity.ok(CourseDetailResponse.from(results))
    }

    @GetMapping
    override fun getAllCourse(
        @AuthenticationPrincipal userId: Long?,
        @RequestParam(required = false, defaultValue = "RECENT") sort: String,
        @RequestParam(required = false, name = "primary_region") primaryRegion: String?,
        @RequestParam(required = false, name = "secondary_region") secondaryRegion: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) limit: Int?,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val query = ReadAllCourseUseCase.Query(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            category = category,
            limit = limit,
            sort = sort,
        )
        val results = readAllCourseUseCase.readAllCourse(query)
        return ResponseEntity.ok(CourseDetailResponse.listFrom(results))
    }

    @GetMapping("/users/{writerId}")
    override fun getAllUserCourse(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable writerId: Long,
        @RequestParam(required = false, defaultValue = "RECENT") sort: String,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val query = ReadAllCourseUseCase.Query(
            userId = userId,
            writerId = writerId,
            sort = sort,
        )
        val results = readAllCourseUseCase.readAllCourse(query)
        return ResponseEntity.ok(CourseDetailResponse.listFrom(results))
    }

    @GetMapping("/users/{targetUserId}/like")
    override fun getAllUserInterestCourse(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable targetUserId: Long,
        @RequestParam(required = false, defaultValue = "RECENT") sort: String,
        @RequestParam(required = false, name = "primary_region") primaryRegion: String?,
        @RequestParam(required = false, name = "secondary_region") secondaryRegion: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) limit: Int?,
    ): ResponseEntity<List<CourseDetailResponse>> {
        val query = ReadAllInterestCourseUseCase.Query(
            userId = userId,
            targetUserId = targetUserId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            category = category,
            limit = limit,
            sort = sort,
        )
        val results = readAllInterestCourseUseCase.readAllUserInterestCourse(query)
        return ResponseEntity.ok(CourseDetailResponse.listFrom(results))
    }

    @PostMapping
    override fun createCourse(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateCourseRequest,
    ): ResponseEntity<CreateCourseResponse> {
        val command = request.toCommand(userId)
        val results = createCourseUseCase.createCourse(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateCourseResponse(results))
    }

    @PostMapping("/{courseId}/like")
    override fun addCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        val command = CreateInterestCourseUseCase.Command(userId, courseId)
        createInterestCourseUseCase.createInterestCourse(command)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{courseId}")
    override fun updateCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: UpdateCourseRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId, courseId)
        updateCourseUseCase.updateCourse(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{courseId}")
    override fun deleteCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        val command = DeleteCourseUseCase.Command(userId, courseId)
        deleteCourseUseCase.deleteCourse(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{courseId}/like")
    override fun deleteCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit> {
        val command = DeleteInterestCourseUseCase.Command(userId, courseId)
        deleteInterestCourseUseCase.deleteInterestCourse(command)
        return ResponseEntity.ok().build()
    }
}

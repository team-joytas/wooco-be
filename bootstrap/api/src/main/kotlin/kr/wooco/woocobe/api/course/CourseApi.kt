package kr.wooco.woocobe.api.course

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.course.request.CreateCourseRequest
import kr.wooco.woocobe.api.course.request.UpdateCourseRequest
import kr.wooco.woocobe.api.course.response.CourseDetailResponse
import kr.wooco.woocobe.api.course.response.CreateCourseResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "코스 API")
interface CourseApi {
    @SecurityRequirement(name = "JWT")
    fun getCourseDetail(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable courseId: Long,
    ): ResponseEntity<CourseDetailResponse>

    @SecurityRequirement(name = "JWT")
    fun getAllCourse(
        @AuthenticationPrincipal userId: Long?,
        @Parameter(
            examples = [
                ExampleObject(name = "최신순", value = "RECENT"),
                ExampleObject(name = "인기순", value = "POPULAR"),
            ],
        )
        @RequestParam sort: String,
        @Parameter(
            description = "시/도",
            example = "서울",
        )
        @RequestParam primaryRegion: String?,
        @Parameter(
            description = "구/군",
            example = "노원",
        )
        @RequestParam secondaryRegion: String?,
        @Parameter(
            description = "전체 카테고리 검색시 파라미터를 지정하지 않습니다",
            examples = [
                ExampleObject(name = "맛집", value = "FAMOUS_RESTAURANT"),
                ExampleObject(name = "활동", value = "ACTIVITY"),
                ExampleObject(name = "핫플레이스", value = "HOT_PLACE"),
                ExampleObject(name = "문화/예술", value = "CULTURE_AND_ART"),
                ExampleObject(name = "기타", value = "ETC"),
            ],
        )
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) limit: Int?,
    ): ResponseEntity<List<CourseDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun getAllUserCourse(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable writerId: Long,
        @Parameter(
            description = "정렬 조건",
            examples = [
                ExampleObject(name = "최신순", value = "RECENT"),
                ExampleObject(name = "인기순", value = "POPULAR"),
            ],
        )
        @RequestParam sort: String,
    ): ResponseEntity<List<CourseDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun getAllUserInterestCourse(
        @AuthenticationPrincipal userId: Long?,
        @PathVariable targetUserId: Long,
        @RequestParam sort: String,
        @RequestParam primaryRegion: String?,
        @RequestParam secondaryRegion: String?,
        @RequestParam category: String?,
        @RequestParam limit: Int?,
    ): ResponseEntity<List<CourseDetailResponse>>

    @SecurityRequirement(name = "JWT")
    fun createCourse(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateCourseRequest,
    ): ResponseEntity<CreateCourseResponse>

    @SecurityRequirement(name = "JWT")
    fun addCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun updateCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
        @RequestBody request: UpdateCourseRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun deleteCourse(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    fun deleteCourseLike(
        @AuthenticationPrincipal userId: Long,
        @PathVariable courseId: Long,
    ): ResponseEntity<Unit>
}

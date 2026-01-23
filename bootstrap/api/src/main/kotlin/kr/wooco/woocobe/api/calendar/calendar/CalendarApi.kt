package kr.wooco.woocobe.api.calendar.calendar

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.calendar.calendar.response.CalendarDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Tag(name = "캘린더 통합 조회 API")
interface CalendarApi {

    @SecurityRequirement(name = "JWT")
    @Operation(
        summary = "캘린더 조회",
        description = "날짜 조건을 기반으로 캘린더를 조회합니다."
    )
    fun readCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate,
        @RequestParam(required = false) groupIds: List<Long>?,
    ): ResponseEntity<List<CalendarDetailResponse>>
}

package kr.wooco.woocobe.api.calendar.calendar

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.calendar.calendar.request.DailyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.calendar.request.MonthlyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.calendar.request.WeeklyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.calendar.response.CalendarDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "캘린더 통합 조회 API")
interface CalendarApi {

    @SecurityRequirement(name = "JWT")
    @Operation(
        summary = "월간 캘린더 조회",
        description = "특정 연도(year), 월(month)에 해당하는 캘린더를 조회합니다."
    )
    fun readMonthlyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: MonthlyCalendarQueryRequest
    ): ResponseEntity<List<CalendarDetailResponse>>

    @SecurityRequirement(name = "JWT")
    @Operation(
        summary = "주간 캘린더 조회",
        description = "특정 연도(year), 월(month), 주차(week)에 해당하는 캘린더를 조회합니다."
    )
    fun readWeeklyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: WeeklyCalendarQueryRequest,
    ): ResponseEntity<List<CalendarDetailResponse>>

    @SecurityRequirement(name = "JWT")
    @Operation(
        summary = "일간 캘린더 조회",
        description = "특정 날짜(date) 해당하는 캘린더를 조회합니다."
    )
    fun readDailyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: DailyCalendarQueryRequest,
    ): ResponseEntity<List<CalendarDetailResponse>>
}

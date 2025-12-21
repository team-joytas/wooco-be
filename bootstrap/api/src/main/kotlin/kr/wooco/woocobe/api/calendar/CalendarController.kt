package kr.wooco.woocobe.api.calendar

import kr.wooco.woocobe.api.calendar.request.DailyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.request.MonthlyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.request.WeeklyCalendarQueryRequest
import kr.wooco.woocobe.api.calendar.response.CalendarDetailResponse
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadDailyCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadMonthlyCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadWeeklyCalendarUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO: API 스펙 협의 필요 (groupIds 등 복잡한 검색 조건 -> GET vs POST)
@RestController
@RequestMapping("/api/v1/calendars")
class CalendarController(
    private val readDailyCalendarUseCase: ReadDailyCalendarUseCase,
    private val readWeeklyCalendarUseCase: ReadWeeklyCalendarUseCase,
    private val readMonthlyCalendarUseCase: ReadMonthlyCalendarUseCase,
) : CalendarApi {

    @PostMapping("/monthly")
    override fun readMonthlyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: MonthlyCalendarQueryRequest,
    ): ResponseEntity<List<CalendarDetailResponse>> {
        val results = readMonthlyCalendarUseCase.readMonthlyCalendar(request.toQuery(userId))
        return ResponseEntity.ok(CalendarDetailResponse.listFrom(results))
    }

    @PostMapping("/weekly")
    override fun readWeeklyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: WeeklyCalendarQueryRequest
    ): ResponseEntity<List<CalendarDetailResponse>> {
        val results = readWeeklyCalendarUseCase.readWeeklyCalendar(request.toQuery(userId))
        return ResponseEntity.ok(CalendarDetailResponse.listFrom(results))
    }

    @PostMapping("/daily")
    override fun readDailyCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: DailyCalendarQueryRequest,
    ): ResponseEntity<List<CalendarDetailResponse>> {
        val results = readDailyCalendarUseCase.readDailyCalendar(request.toQuery(userId))
        return ResponseEntity.ok(CalendarDetailResponse.listFrom(results))
    }
}

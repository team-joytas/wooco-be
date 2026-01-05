package kr.wooco.woocobe.api.calendar.calendar

import kr.wooco.woocobe.api.calendar.calendar.response.CalendarDetailResponse
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadCalendarUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/calendars")
class CalendarController(
    private val readCalendarUseCase: ReadCalendarUseCase,
) : CalendarApi {
    @GetMapping
    override fun readCalendar(
        @AuthenticationPrincipal userId: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate,
        @RequestParam(required = false) groupIds: List<Long>?,
    ): ResponseEntity<List<CalendarDetailResponse>> {
        val results = readCalendarUseCase.readCalendar(
            ReadCalendarUseCase.Query(
                userId = userId,
                startDate = startDate,
                endDate = endDate,
                groupIds = groupIds,
            )
        )
        return ResponseEntity.ok(CalendarDetailResponse.listFrom(results))
    }
}

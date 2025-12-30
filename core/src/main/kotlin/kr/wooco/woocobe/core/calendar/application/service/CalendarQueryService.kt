package kr.wooco.woocobe.core.calendar.application.service

import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadDailyCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadMonthlyCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadWeeklyCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.out.dto.CalendarView
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupQueryPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanQueryPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class CalendarQueryService(
    private val groupQueryPort: GroupQueryPort,
    private val schedulePlanQueryPort: SchedulePlanQueryPort,
    private val placeQueryPort: PlaceQueryPort,
) : ReadDailyCalendarUseCase,
    ReadWeeklyCalendarUseCase,
    ReadMonthlyCalendarUseCase {
    override fun readDailyCalendar(query: ReadDailyCalendarUseCase.Query): List<CalendarView> {
        val groupIds = resolveGroupIds(
            userId = query.userId,
            groupIds = query.groupIds ?: emptyList()
        )

        val plans = schedulePlanQueryPort.getViewAllByGroupIdInAndVisitDate(
            groupIds = groupIds,
            visitDate = query.date
        )

        return buildCalendarViews(plans)
    }

    override fun readWeeklyCalendar(query: ReadWeeklyCalendarUseCase.Query): List<CalendarView> {
        require(query.week in 1..5) { "week must be between 1 and 5" }

        val (startDate, endDate) = dateRangeOfWeek(year = query.year, month = query.month, week = query.week)

        val groupIds = resolveGroupIds(userId = query.userId, groupIds = query.groupIds ?: emptyList())
        val plans = schedulePlanQueryPort.getViewAllByGroupIdInAndVisitDateBetween(
            groupIds = groupIds,
            startDate = startDate,
            endDate = endDate
        )

        return buildCalendarViews(plans)
    }

    override fun readMonthlyCalendar(query: ReadMonthlyCalendarUseCase.Query): List<CalendarView> {
        val (startDate, endDate) = dateRangeOfMonth(year = query.year, month = query.month)

        val groupIds = resolveGroupIds(userId = query.userId, groupIds = query.groupIds ?: emptyList())
        val plans = schedulePlanQueryPort.getViewAllByGroupIdInAndVisitDateBetween(groupIds, startDate, endDate)

        return buildCalendarViews(plans)
    }

    private fun buildCalendarViews(planViews: List<PlanView>): List<CalendarView> {
        val groupIds = planViews.map { it.groupId }.distinct()
        val groups = groupQueryPort.getViewAllByIds(groupIds).associateBy { it.id }
        val placeIds = planViews.flatMap { p -> p.places.map { it.placeId } }.distinct()
        val places = placeQueryPort.getAllByPlaceIds(placeIds).associateBy { it.id }

        return planViews.map { planView ->
            val groupView = requireNotNull(groups[planView.groupId])
            CalendarView(
                planId = planView.id,
                groupId = planView.groupId,
                groupName = groupView.name,
                groupSize = groupView.groupSize,
                title = planView.title,
                visitDate = planView.visitDate,
                places = planView.places.map { placeView ->
                    val place = requireNotNull(places[placeView.placeId])
                    CalendarView.CalendarPlaceView(
                        id = place.id,
                        name = place.name,
                        address = place.address,
                        thumbnailUrl = place.thumbnailUrl,
                    )
                }
            )
        }
    }

    private fun resolveGroupIds(userId: Long, groupIds: List<Long>): List<Long> {
        val groups = groupQueryPort.getViewAllByUserId(userId)
        if (groupIds.isEmpty()) {
            return groups.map { it.id }
        }
        return groupIds.filter { id -> groups.any { it.id == id } }
    }

    private fun dateRangeOfMonth(year: Int, month: Int): Pair<LocalDate, LocalDate> {
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())
        return startDate to endDate
    }

    private fun dateRangeOfWeek(year: Int, month: Int, week: Int): Pair<LocalDate, LocalDate> {
        val firstDay = LocalDate.of(year, month, 1)
        val firstWeekStart = firstDay.with(DayOfWeek.MONDAY)

        val startDate = firstWeekStart.plusWeeks((week - 1).toLong())
        val endDate = startDate.plusDays(6)

        return startDate to endDate
    }
}

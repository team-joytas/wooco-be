package kr.wooco.woocobe.core.calendar.application.service

import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadCalendarUseCase
import kr.wooco.woocobe.core.calendar.application.port.`in`.results.CalendarResult
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupQueryPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanQueryPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import org.springframework.stereotype.Service

// TODO: 검증 로직 => Policy? 협의 필요
@Service
class CalendarQueryService(
    private val placeQueryPort: PlaceQueryPort,
    private val groupQueryPort: GroupQueryPort,
    private val schedulePlanQueryPort: SchedulePlanQueryPort,
) : ReadCalendarUseCase {
    override fun readCalendar(query: ReadCalendarUseCase.Query): List<CalendarResult> {
        val userGroupIds = groupQueryPort.getViewAllByUserId(query.userId).map { it.id }
        val resolvedGroupIds = resolveGroupIds(
            groupIds = query.groupIds,
            userGroupIds = userGroupIds,
        )
        val planViews = schedulePlanQueryPort.getViewAllByGroupIdInAndVisitDateBetween(
            groupIds = resolvedGroupIds,
            startDate = query.startDate,
            endDate = query.endDate,
        )

        val groupMap = groupQueryPort
            .getViewAllByIds(planViews.map { it.groupId }.distinct())
            .associateBy { it.id }
        val placeIds = planViews
            .flatMap { it.places }
            .map { it.placeId }
            .distinct()
        val placeMap = placeQueryPort
            .getAllByPlaceIds(placeIds)
            .associateBy { it.id }
        return CalendarResult.listFrom(
            planViews = planViews,
            groupMap = groupMap,
            placeMap = placeMap,
        )
    }

    private fun resolveGroupIds(
        groupIds: List<Long>?,
        userGroupIds: List<Long>,
    ): List<Long> {
        if (groupIds.isNullOrEmpty()) {
            return userGroupIds
        }
        val allowed = userGroupIds.asSequence().toSet()
        return groupIds.filter { it in allowed }
    }
}
